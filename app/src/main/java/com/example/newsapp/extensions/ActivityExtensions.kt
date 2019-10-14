package com.example.newsapp.extensions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.newsapp.utils.FragOperations

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.popBackStack() {
    this.hideKeyboard()
    supportFragmentManager.popBackStack()
}

fun AppCompatActivity.popBackStackInclusive() {
    this.hideKeyboard()
    if (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStack(supportFragmentManager.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

fun AppCompatActivity.executeFragOperation(fragment: Fragment, frameId: Int, fragOperation: FragOperations) {
    when(fragOperation) {
        FragOperations.ADD -> {
            supportFragmentManager.inTransaction { add(frameId, fragment, fragment.javaClass.simpleName) }
        }
        FragOperations.REPLACE -> {
            supportFragmentManager.inTransaction { replace(frameId, fragment, fragment.javaClass.simpleName) }
        }
        FragOperations.REPLACE_WITH_BACKSTACK -> {
            supportFragmentManager.inTransaction { replace(frameId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName) }
        }
    }
}

fun AppCompatActivity.getCurrentFragment() : Fragment? {
    val fragmentManager = supportFragmentManager
    var fragmentTag: String? = ""

    if (fragmentManager.backStackEntryCount > 0)
        fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name

    return fragmentManager.findFragmentByTag(fragmentTag)
}

inline fun <T: Fragment> T.withArguments(argsBuilder: Bundle.() -> Unit): T = this.apply { arguments = Bundle().apply(argsBuilder) }