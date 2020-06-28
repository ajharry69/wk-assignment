package com.xently.persona.ui.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.xently.persona.BuildConfig
import com.xently.persona.R
import com.xently.persona.data.TaskResult
import com.xently.persona.data.errorMessage
import com.xently.persona.data.model.Location
import com.xently.persona.data.model.Person
import com.xently.persona.data.source.SourceOrDestination
import com.xently.persona.databinding.AddPersonFragmentBinding
import com.xently.persona.utils.setImageFromPath
import com.xently.xui.Fragment
import com.xently.xui.utils.ui.fragment.requestFeaturePermission
import com.xently.xui.utils.ui.fragment.setupToolbar
import com.xently.xui.utils.ui.fragment.showSnackBar
import com.xently.xui.utils.ui.view.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class AddPersonFragment : Fragment() {

    private var location: Location? = null
    private var photoFile: File? = null
    private var currentPhotoPath: String? = null
    private val viewModel: AddPersonViewModel by viewModels()
    private var _binding: AddPersonFragmentBinding? = null
    private val binding: AddPersonFragmentBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestFeaturePermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            LOCATION_PERMISSION_REQUEST_CODE,
            { onLocationPermissionGranted() }) { onLocationPermissionRationaleNeeded() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddPersonFragmentBinding.inflate(inflater, container, false).apply {
            setupToolbar(toolbar)
            nameContainer.addTextChangeListener()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activateClickListeners()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.personResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is TaskResult.Success -> {
                    binding.run {
                        val message =
                            getString(R.string.person_added_successfully, name.text.toString())
                        showSnackBar(message)
                        clearText(name)
                        hideViews(progress)
                        enableViews(captureLocation, saveLocal, upload, list)
                    }
                }
                is TaskResult.Error -> {
                    it.errorMessage?.let { message -> showSnackBar(message, Snackbar.LENGTH_LONG) }
                    binding.run {
                        hideViews(progress)
                        enableViews(captureLocation, saveLocal, upload, list)
                    }
                }
                TaskResult -> {
                    binding.run {
                        showViews(progress)
                        disableViews(captureLocation, saveLocal, list, upload)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_INTENT_REQUEST_CODE) {
            data?.data?.also {
                photoFile = it.toFile()
                val thumbnail = data.extras?.get("data") as? Bitmap
                with(binding.photo) {
                    if (thumbnail != null) setImageBitmap(thumbnail) else {
                        if (currentPhotoPath == null) {
                            setImageURI(it)
                        } else {
                            setImageFromPath(currentPhotoPath!!)
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val isPermissionGranted =
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (isPermissionGranted) {
                    onCameraPermissionGranted()
                    return
                }
                // permission denied! Disable the functionality that depends on this permission.
                return
            }
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (isPermissionGranted) {
                    onLocationPermissionGranted()
                    return
                }
                // permission denied! Disable the functionality that depends on this permission.
                return
            }
            // Add other 'when' lines to check for other permissions this app might request.
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun activateClickListeners() {
        with(binding) {
            takePhoto.setClickListener {
                requestFeaturePermission(
                    Manifest.permission.CAMERA,
                    CAMERA_PERMISSION_REQUEST_CODE,
                    { onCameraPermissionGranted() }
                ) { onCameraPermissionRationaleNeeded() }
            }
            saveLocal.setClickListener {
                addPerson(SourceOrDestination.LOCAL)
            }
            list.setOnClickListener(
                Navigation.createNavigateOnClickListener(AddPersonFragmentDirections.peopleList())
            )
            upload.setClickListener {
                addPerson()
            }
            captureLocation.setClickListener {
                // TODO: Implement...
                showSnackBar("TODO: capture location")
            }
        }
    }

    private fun addPerson(destination: SourceOrDestination? = null) {
        val person = getPersonFromInputs() ?: return
        viewModel.addPerson(person, photoFile, destination)
    }

    private fun getPersonFromInputs(): Person? {
        return binding.run {
            val name: String? = name.text.toString()
            val gender: Person.Gender = try {
                Person.Gender.valueOf(gender.selectedItem.toString())
            } catch (ex: Exception) {
                showSnackBar(R.string.error_gender_required, Snackbar.LENGTH_LONG)
                return@run null
            }
            if (name.isNullOrBlank()) {
                nameContainer.setErrorText(R.string.error_field_required)
                return@run null
            }
            Person(name = name, gender = gender, location = getLocationFromMap())
        }
    }

    private fun getLocationFromMap(): Location {
        if (location == null) location = Location()
        return location!!
    }

    private fun onLocationPermissionGranted() {

    }

    private fun onLocationPermissionRationaleNeeded() {

    }

    private fun onCameraPermissionGranted() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                val photoFile: File? = try {
                    File.createTempFile(
                        "JPEG_${System.currentTimeMillis()}",
                        ".jpg",
                        requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    ).apply {
                        // Save a file: path for use with ACTION_VIEW intents
                        currentPhotoPath = absolutePath
                    }
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoUri = FileProvider.getUriForFile(
                        requireContext(),
                        "${BuildConfig.APPLICATION_ID}.file_provider",
                        it
                    )

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(intent, CAMERA_INTENT_REQUEST_CODE)
                }
            }
        }
    }

    private fun onCameraPermissionRationaleNeeded() {

    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
        private const val LOCATION_PERMISSION_REQUEST_CODE = 321
        private const val CAMERA_INTENT_REQUEST_CODE = 1234
    }
}