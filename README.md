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
