
package com.bombadu.pixablast.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bombadu.pixablast.R
import com.bombadu.pixablast.data.local.LocalData
import com.bombadu.pixablast.databinding.ActivityAddEntryBinding
import com.bombadu.pixablast.util.EntryUtil
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddEntryActivity : AppCompatActivity() {

    private lateinit var url: String

    private var hasImage = false
    private lateinit var viewModel: ImagePostViewModel

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                url = intent.extras?.getString("key").toString()
                Picasso.get().load(url).into(binding.imageView)
                hasImage = true

            }
        }
    }

    private lateinit var binding: ActivityAddEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.imageView.setOnClickListener {
            startForResult.launch(Intent(this, ImageSearchActivity::class.java))

        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_entry_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                if (!hasImage) {
                    Toast.makeText(this, "Data Missing..", Toast.LENGTH_SHORT).show()
                } else {
                    val caption = binding.captionEditText.text.toString()
                    val name = binding.nameEditText.text.toString()

                    if (EntryUtil.validateEntry(caption, name)) {
                        saveEntry(caption, name, url)
                    }
                    hasImage = false
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveEntry(caption: String, name: String, url: String) {
        viewModel = ViewModelProvider(this).get(ImagePostViewModel::class.java)
        val newEntry = LocalData(url, caption, name)
        viewModel.insertEntry(newEntry)
        finish()




    }


}