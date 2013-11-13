package android.lib.hovertip;

import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.samsung.spen.lib.input.SPenEventLibrary;
import com.samsung.spensdk.applistener.SPenHoverListener;

/**
 * A {@link HoverTip} is class that helps create and show a view containing a quick little message for the user.
 * <p>When the user long-clicks on or uses <a href="http://developer.samsung.com/android/tools-sdks/S-Pen-SDK-2-3">Samsung S-Pen</a>
 * to hover over a given view, a {@link HoverTip} is shown to the user as a floating {@link PopupWindow}
 * over the view. It will never receive focus. The idea is to hint the user about the function or meaning of a given view.</p>
 */
public class HoverTip {
    /**
     * Show the view or text notification for a short period of time.
     */
    public static final int LENGTH_SHORT = 2000;

    /**
     * Show the view or text notification for a long period of time.
     */
    public static final int LENGTH_LONG = 3500;

    private final View view;

    private PopupWindow  popupWindow;
    private CharSequence tip;
    private int          duration = HoverTip.LENGTH_SHORT;

    /**
     * Creates a new instance of {@link HoverTip} associated with the given <code>view</code>.
     * @param view the view associated with the {@link HoverTip}.
     */
    public HoverTip(final View view) {
        this.view = view;
    }

    /**
     * Cleans up any resources used by the view.
     * <p>Applications that uses {@link HoverTip} must call {@link #onPause()} in its {@link android.app.Activity#onPause()}
     * to closes the view if it is showing. It is safe to always call {@link #onPause()} even if the view is not showing.</p>
     */
    public void onPause() {
        this.cancel();
    }

    /**
     * Closes the view if it is showing.
     */
    public void cancel() {
        if (this.popupWindow != null) {
            this.popupWindow.dismiss();
        }
    }

    /**
     * Shows the view for period of time.
     * <p>The view will show only after {@link #setTip(CharSequence, int)} or {@link #setTip(int, int)} is called at least once.</p>
     */
    public void show() {
        if (this.popupWindow != null) {
            this.popupWindow.showAsDropDown(this.view);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    HoverTip.this.getPopupWindow().dismiss();
                }
            }, this.duration);
        }
    }

    /**
     * Returns the text that is associated with the given view as a hover tip.
     * @return the text that is associated with the given view as a hover tip.
     */
    public CharSequence getTip() {
        return this.tip;
    }

    /**
     * Returns the duration to display the message in millisecond.
     * @return the duration to display the message in millisecond.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Sets a hover tip for the given view.
     * @param tip the text to show.
     * @param duration the duration to display the message in millisecond.
     * <p>For convenience, {@link #LENGTH_SHORT} and {@link #LENGTH_LONG} can be used.</p>
     */
    public void setTip(final CharSequence tip, final int duration) {
        this.tip      = tip;
        this.duration = duration;

        final View tipView = View.inflate(this.view.getContext(), R.layout.transient_notification, null);
        ((TextView)tipView.findViewById(R.id.message)).setText(tip);

        this.popupWindow = new PopupWindow(tipView);
        this.popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final PopupWindow popupWindow = HoverTip.this.getPopupWindow();

                if (popupWindow != null) {
                    popupWindow.showAsDropDown(view);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.dismiss();
                        }
                    }, duration);
                }

                return true;
            }
        });

        final SPenEventLibrary library = new SPenEventLibrary();

        if (SPenEventLibrary.isHoverListenerSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            library.setSPenHoverListener(this.view, new SPenHoverListener() {
                @Override
                public boolean onHover(final View view, final MotionEvent event) {
                    final PopupWindow window = HoverTip.this.getPopupWindow();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_HOVER_ENTER:
                            if (window != null) {
                                window.showAsDropDown(view);
                            }

                            return true;

                        case MotionEvent.ACTION_HOVER_EXIT:
                            if (window != null) {
                                window.dismiss();
                            }

                            return true;

                        default:
                            return false;
                    }
                }

                @Override
                public void onHoverButtonDown(final View view, final MotionEvent event) {
                }

                @Override
                public void onHoverButtonUp(final View view, final MotionEvent event) {
                }
            });
        }
    }

    /**
     * Sets a hover tip for the given view.
     * @param resId the resource ID of the string resource to use.
     * @param duration the duration to display the message in millisecond.
     * <p>For convenience, {@link #LENGTH_SHORT} and {@link #LENGTH_LONG} can be used.</p>
     */
    public void setTip(final int resId, final int duration) {
        this.setTip(this.view.getContext().getText(resId), duration);
    }

    /**
     * Returns the view associated with the {@link HoverTip}.
     * @return the view associated with the {@link HoverTip}.
     */
    protected View getView() {
        return this.view;
    }

    /**
     * Returns the {@link PopupWindow} that hosts the text to show.
     * @return the {@link PopupWindow} that hosts the text to show.
     */
    protected PopupWindow getPopupWindow() {
        return this.popupWindow;
    }

    /**
     * Makes a hover tip that shows a text view.
     * @param tip the text to show.
     * @param duration the duration to display the message in millisecond.
     * <p>For convenience, {@link #LENGTH_SHORT} and {@link #LENGTH_LONG} can be used.</p>
     * @return a {@link HoverTip} instance.
     */
    public static HoverTip makeTip(final View view, final CharSequence tip, final int duration) {
        final HoverTip hoverTip = new HoverTip(view);

        hoverTip.setTip(tip, duration);

        return hoverTip;
    }

    /**
     * Makes a hover tip that shows a text view.
     * @param resId the resource ID of the string resource to use.
     * @param duration the duration to display the message in millisecond.
     * <p>For convenience, {@link #LENGTH_SHORT} and {@link #LENGTH_LONG} can be used.</p>
     * @return a {@link HoverTip} instance.
     */
    public static HoverTip makeTip(final View view, final int resId, final int duration) {
        final HoverTip hoverTip = new HoverTip(view);

        hoverTip.setTip(resId, duration);

        return hoverTip;
    }
}
