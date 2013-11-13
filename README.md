HoverTip Android Library
========================

The <a href="https://github.com/ayltai/Android-Lib-HoverTip">HoverTip Android library</a> provides an easy way to show simple text message tip when the user long-clicks on or hovers using <a href="http://developer.samsung.com/android/tools-sdks/S-Pen-SDK-2-3">Samsung S-Pen</a> over a view, similar to what a toast does but the message is associated with a specific view.

A <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip/src/android/lib/hovertip/HoverTip.java">HoverTip</a> provides simple visual feedback about an operation or meaning of a view in a small popup. It only fills the amount of space required for the text message and the current activity remains visible and interactive. HoverTip messages automatically disappear after a timeout.

Installation
------------

To develop an app using <a href="https://github.com/ayltai/Android-Lib-HoverTip">HoverTip Android library</a>, you must <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip">download the library</a> and import it to your IDE.

**Caution:** When you add the <a href="https://github.com/ayltai/Android-Lib-HoverTip">HoverTip Android library</a>, be sure to add it with *resources*, as described <a href="http://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject">here</a>. Simply adding a .jar file to your project will not work. You must follow the directions for referencing a library, or your app won't be able to access the library's resources, and it won'y run properly.

Usage
-----

First, instantiate a <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip/src/android/lib/hovertip/HoverTip.java">HoverTip</a> object with one of the `makeText()` methods. This method takes 3 parameters: the view on which to show a message, the text message, and the duration for the message. It returns a properly initialized <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip/src/android/lib/hovertip/HoverTip.java">HoverTip</a> object, as shown in the following <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip-Samples/src/android/lib/hovertip/samples/MainActivity.java">example</a>:

    HoverTip hoverTip = HoverTip.makeTip(findViewById(R.id.button, R.string.button_tip, HoverTip.LENGTH_SHORT));

This <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip-Samples/src/android/lib/hovertip/samples/MainActivity.java">example</a> demostrates everything you need for most common use cases. You should rarely need anything else. You may, however, want to show <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip/src/android/lib/hovertip/HoverTip.java">HoverTip</a> programmatically, like this:

    hoverTip.show();

You must call `onPause()` method in your activity's `onPause()` to clean up any resources used by <a href="https://github.com/ayltai/Android-Lib-HoverTip/blob/master/HoverTip/src/android/lib/hovertip/HoverTip.java">HoverTip</a>:

    hoverTip.onPause()

Samsung S-Pen Support
---------------------

The library automatically checks for the support of <a href="http://developer.samsung.com/android/tools-sdks/S-Pen-SDK-2-3">Samsung S-Pen</a> on the target platform. If it is supported, both S-Pen and long-click actions will be enabled. Otherwise, only long-clicking on a view will show the text message. So it is safe to use this library on non-Samsung devices.
