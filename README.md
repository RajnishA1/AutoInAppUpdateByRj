# 🚀 Auto In App Update By Rj
Automatically prompt users to update your APK directly from your custom URL — no Play Store required!

<p align="center"> <img src="https://github.com/user-attachments/assets/d999f427-7abf-414b-b6c1-3360301cff00" width="150"/> <img src="https://github.com/user-attachments/assets/99219e7a-adc4-4ec2-8fea-4237b1ef38c4" width="150"/> <img src="https://github.com/user-attachments/assets/f147ca39-31a3-4e79-af81-d40c4dfe7d26" width="150"/> <img src="https://github.com/user-attachments/assets/06d75410-eb14-4658-b6d9-2e6557df9e2f" width="150"/> <img src="https://github.com/user-attachments/assets/78553285-7412-47a5-bb99-509713a48995" width="150"/>  </p>

> 📦 Step 1: Add the JitPack Repository

''' settings.gradle

	dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
           google()
           mavenCentral()
           maven { url = uri("https://jitpack.io") }
      }
}


 > 🧩 Step 2: Add the Dependency

''' app/build.gradle

	dependencies {
    		implementation("com.github.RajnishA1:AutoInAppUpdateByRj:3.0")
	}



> Step 3: Implement in MainActivity

    class MainActivity : AppCompatActivity() {

	    private lateinit var storagePermissionLauncher: ActivityResultLauncher<Array<String>>
	    private lateinit var installPermissionLauncher: ActivityResultLauncher<Intent>
	    private lateinit var manageAllFilesPermissionLauncher: ActivityResultLauncher<Intent>
	    private lateinit var permissionManager: PermissionManager
	
	    override fun onCreate(savedInstanceState: Bundle?) {
	        super.onCreate(savedInstanceState)
	        setContentView(R.layout.activity_main)
	
	        registerPermissionLaunchers()
	
	        permissionManager = PermissionManager(
	            context = this,
	            activity = this,
	            apkUrl = "https://github.com/RajnishA1/updated-apk-url/raw/refs/heads/main/photo.apk", // <-- Put your direct APK link here
	            storagePermissionLauncher = storagePermissionLauncher,
	            installPermissionLauncher = installPermissionLauncher,
	            manageAllFilesPermissionLauncher = manageAllFilesPermissionLauncher
	        )
	
	        permissionManager.checkAndRequestPermissions()
	    }
	
	    private fun registerPermissionLaunchers() {
	        storagePermissionLauncher = registerForActivityResult(
	            ActivityResultContracts.RequestMultiplePermissions()
	        ) { permissions ->
	            if (permissions.values.all { it }) {
	                permissionManager.checkAndRequestPermissions()
	            } else {
	                permissionManager.showWhyPermissionDialog("Storage permission is required to download the update.")
	            }
	        }
	
	        installPermissionLauncher = registerForActivityResult(
	            ActivityResultContracts.StartActivityForResult()
	        ) {
	            permissionManager.checkAndRequestPermissions()
	        }
	
	        manageAllFilesPermissionLauncher = registerForActivityResult(
	            ActivityResultContracts.StartActivityForResult()
	        ) {
	            permissionManager.checkAndRequestPermissions()
	        }
	      }
    }






📝 Notes
	 ✅ Make sure your APK link is a direct download URL.
	
	🛡️ Handles runtime permissions for:
	
	MANAGE_EXTERNAL_STORAGE (Android 11+)
	
	READ_MEDIA_IMAGES (Android 13+)
	
	INSTALL_UNKNOWN_APPS
	
	📦 Downloads and installs the APK after permission is granted.

 💡 Features
	Simple integration — just plug and play
	
	Supports all Android versions (SDK 23+)
	
	Clean permission management via PermissionManager
	
	Fully customizable dialog UI via DialogHelper

🙌 Contributing
	Feel free to fork and contribute! Pull requests are welcome.

 📜 License
	MIT License © 2025 RajnishA1
