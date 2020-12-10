<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="${basePackage}.${fragmentPackage}.${classFolderName}.${fragmentName}">

    <data>
            <variable
                    name="viewModel"
                    type="${basePackage}.${fragmentPackage}.${classFolderName}.${viewModelName}" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:text="Hello ${fragmentName}!"
            app:layout_constraintTop_toTopOf="@+id/appbar"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
