package android.lib.hovertip.samples;

import android.app.Activity;
import android.lib.hovertip.HoverTip;
import android.os.Bundle;

public class MainActivity extends Activity {
    private HoverTip textTip;
    private HoverTip buttonTip;
    private HoverTip imageTip;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        // Creates HoverTip in one step.
        this.textTip = HoverTip.makeTip(this.findViewById(R.id.instruction), R.string.instruction_tip, HoverTip.LENGTH_SHORT);

        // Creates HoverTip in multiple steps.
        this.buttonTip = new HoverTip(this.findViewById(R.id.button));
        this.buttonTip.setTip(this.getText(R.string.button_tip), 3000);

        this.imageTip = HoverTip.makeTip(this.findViewById(R.id.image), R.string.image_tip, HoverTip.LENGTH_LONG);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.textTip.onPause();
        this.buttonTip.onPause();
        this.imageTip.onPause();
    }
}
