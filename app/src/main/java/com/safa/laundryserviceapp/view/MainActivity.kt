package com.safa.laundryserviceapp.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.safa.laundryserviceapp.R
import com.safa.laundryserviceapp.fragment.FirstCategoryFragment
import com.safa.laundryserviceapp.fragment.SecoundCategoryFragment
import com.safa.laundryserviceapp.utility.CustomSharedPreferences
import com.safa.laundryserviceapp.utility.ENGLISH
import com.safa.laundryserviceapp.utility.SPANISH
import com.safa.laundryserviceapp.utility.USER_NAME
import com.safa.laundryserviceapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var name: String
    lateinit var toolbar: ActionBar
    var lastButton: Button? = null

    lateinit var menu: Menu

    private lateinit var viewModel: MainActivityViewModel
    lateinit var customSharedPreferences: CustomSharedPreferences
    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var replaceableCategoryFragment: Fragment

    companion object{
        private val PERMISSION_CODE = 1;
        private val IMAGE_PICK_CODE = 2;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configFirstShow()
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        customSharedPreferences = CustomSharedPreferences(this)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        name = getString(R.string.user_name)
        intent.getStringExtra(USER_NAME)?.let {
            if (!intent.getStringExtra(USER_NAME).equals(""))
            name = intent.getStringExtra(USER_NAME)
        }


        val welcomeString = getString(R.string.user_welcome)+" "+name + "!"
        user_name_textView.setText(welcomeString)


        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.refreshData()



        swipeRefreshLayout.setOnRefreshListener {
            configFirstShow()
            viewModel.refreshDataFromApi()
            swipeRefreshLayout.isRefreshing = false
        }
        observeData()
        enableFirstCategory()

    }

    fun enableFirstCategory(){
        category_1.setTextColor(getColor(R.color.colorSelectedCategory))
        replaceableCategoryFragment = FirstCategoryFragment()
        replaceFragment(replaceableCategoryFragment)

    }

    fun configFirstShow(){
        profile_linearLayout.visibility = View.GONE
        category_toolbar.visibility = View.GONE
        navigationView.visibility = View.GONE
        content_frameLayout.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun observeData(){
        viewModel.categories.observe(this, Observer {categories->

                if (categories != null) {
                    for (x in 0..categories.size){
                        when (x) {
                            0 -> category_1.setText(categories.get(x).category_title)
                            1 -> category_2.setText(categories.get(x).category_title)
                            2 -> category_3.setText(categories.get(x).category_title)
                            3 -> category_4.setText(categories.get(x).category_title)
                            4 -> category_5.setText(categories.get(x).category_title)
                            5 -> category_6.setText(categories.get(x).category_title)

                            else -> { // Note the block
                                print(" x size > category size")
                            }
                        }

                        loadingObserve()
                    }
                }

        })



    }

     fun loadingObserve(){
        viewModel.loading.observe(this, Observer {
            if(it){
                profile_linearLayout.visibility = View.VISIBLE
                category_toolbar.visibility = View.VISIBLE
                navigationView.visibility = View.VISIBLE
                content_frameLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_navigation, menu)
        this.menu = menu

        if (customSharedPreferences.returnLanguage() == 1){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.icons_greatbritain));
        }else if(customSharedPreferences.returnLanguage() == 2){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.icons_spain_flag));
        }

        languageChangeObserve()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_language -> {
                if (customSharedPreferences.returnLanguage() == 1){
                    changeLanguage(SPANISH)

                }else if(customSharedPreferences.returnLanguage() == 2){
                    changeLanguage(ENGLISH)
                }

                return true
            }
            R.id.navigation_basket -> {
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun changeLanguage(language: Int){
        customSharedPreferences.enterLanguage(language)
        viewModel.changeLanguage(language)
    }

    fun languageChangeObserve(){
        viewModel.language.observe(this, Observer {
            if (customSharedPreferences.returnLanguage() == 1){
                menu.getItem(0).setIcon(getDrawable(R.drawable.icons_greatbritain))
            }else{
                menu.getItem(0).setIcon(getDrawable(R.drawable.icons_spain_flag))
            }
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chat -> {

                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true


            }
            R.id.navigation_order -> {

                val intent = Intent(this, OrdersActivity::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_more -> {

                val intent = Intent(this, MoreActivity::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun onSelectPicture(view: View) {
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE )
        }else{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

    }



    fun onSelectedCategory(view: View) {

        val button: Button = view as Button
          if (lastButton == null){
              lastButton = view as Button
        }else{
              lastButton?.setTextColor(getColor(R.color.colorUnSelectedCategory))
              lastButton = button
        }
        button.setTextColor(getColor(R.color.colorSelectedCategory))

        println("view tag "+view.tag)
        when (view.getTag()){
            category_1.getTag()->{
                replaceableCategoryFragment = FirstCategoryFragment()
                replaceFragment(replaceableCategoryFragment)
            }
            category_2.getTag()->{
                replaceableCategoryFragment = SecoundCategoryFragment()
                replaceFragment(replaceableCategoryFragment)
            }

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) { //fragment not in back stack, create it.
            val ft = manager.beginTransaction()
            ft.replace(R.id.content_frameLayout, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    pickImageFromGallery()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null){

            user_imageView.setImageURI(data?.data)

        }
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }




}
