# Auto In App Library By Rj

> Step 1. Add the JitPack repository to your build file

''' gradle

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}

 > Step 2. Add the dependency

''' gradle

> dependencies {
>
> // add this into your build.gradle file
> 
     implementation("com.github.RajnishA1:AutoInAppUpdateByRj:1.0.0")
}

> step 3 In main activity
>
>      // Check if storage permission is granted
        if (!PermissionUtils.hasStoragePermission(this)) {
            PermissionUtils.requestStoragePermission(this)
        } else if (!PermissionUtils.hasInstallPermission(this)) {
            PermissionUtils.requestInstallPermission(this)
        } else {
            // If both permissions are granted, show the update dialog
            showUpdateDialog()
        }

    override fun onResume() {
        super.onResume()
        if (!PermissionUtils.hasStoragePermission(this)) {
            PermissionUtils.requestStoragePermission(this)
        } else if (!PermissionUtils.hasInstallPermission(this)) {
            PermissionUtils.requestInstallPermission(this)
        } else {
            // If both permissions are granted, show the update dialog
            showUpdateDialog()
        }
    }

     // Function to show update dialog
    private fun showUpdateDialog() {
        DialogHelper.showUpdateDialog(this, "https://github.com/RajnishA1/updated-apk-url/raw/refs/heads/main/photo.apk")
    }

