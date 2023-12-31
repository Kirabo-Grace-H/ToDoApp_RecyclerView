package com.kotlin.toapp_recyclerview

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.kotlin.toapp_recyclerview.databinding.ActivityAddBinding
import com.kotlin.toapp_recyclerview.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Calendar
import java.util.UUID

class AddActivity : AppCompatActivity() {
    val cameraCode = 1
    val fileCode = 2
    lateinit var binding:ActivityAddBinding
    var imageUri: Uri?= null
    var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val image  = binding.activityIV
        image.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA,cameraCode)
        }

        val fileCapture = binding.fileCapture
        fileCapture.setOnClickListener {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, fileCode)
        }

        val activityName = binding.etName.text.toString()
        val activityDescription = binding.etDesActivity.text.toString()

        binding.etDatetime.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    binding.etDatetime.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

        binding.etDatetime.setOnClickListener {
            // Get Current Time
            // Get Current Time
            val c = Calendar.getInstance()
            val mHour = c[Calendar.HOUR_OF_DAY]
            val mMinute = c[Calendar.MINUTE]

            // Launch Time Picker Dialog

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(this,
                { view, hourOfDay, minute ->
                    binding.etDatetime.text = "$hourOfDay:$minute"
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }

        val dateTime =  binding.etDatetime.text.toString() + binding.etDatetime.text.toString()
        binding.saveActivity.setOnClickListener {
            coroutineScope.launch(Dispatchers.IO) {
                val db = DatabaseBuilder().buildDB(this@AddActivity).activityDao().insertActivity(
                    ActivityModel(1, imageUri.toString(), activityName, dateTime, activityDescription,
                        ActivityStatus.Completed
                    )
                )
                //create a notification

                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@AddActivity, MainActivity::class.java))
                }
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                //val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "New Picture")
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

                //camera intent
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                // set filename
                val x = UUID.randomUUID()
                //val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val vFilename =  "$x.png"
                val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

                // set direcory folder
                val file = File(directory, vFilename);
                val image_uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

                startActivityForResult(takePictureIntent, cameraCode)
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        else if(requestCode == fileCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                val filePictureIntent = Intent(MediaStore.ACTION_PICK_IMAGES)
                startActivityForResult(filePictureIntent, fileCode)
            } else {
                Toast.makeText(this, "File Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
//        else if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraCode && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.activityIV.setImageBitmap(imageBitmap)
        } else if(requestCode == fileCode && resultCode == RESULT_OK) {
            val imageBitmap = data?.data
            imageUri = imageBitmap
            binding.fileCapture.setImageURI(imageBitmap)
        }
    }

    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@AddActivity, arrayOf(permission), requestCode)
        } else {
            if (requestCode == 1) {

                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "New Picture")
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

                //camera intent
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                // set filename
                val x = UUID.randomUUID()
                //val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val vFilename =  "$x.png"
                val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

                // set direcory folder
                val file = File(directory, vFilename);
                val image_uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)


                startActivityForResult(takePictureIntent, cameraCode)
            } else if(requestCode == 2) {
                val filePictureIntent = Intent(MediaStore.ACTION_PICK_IMAGES)
                startActivityForResult(filePictureIntent, fileCode)
                //Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            }

        }
    }

}
