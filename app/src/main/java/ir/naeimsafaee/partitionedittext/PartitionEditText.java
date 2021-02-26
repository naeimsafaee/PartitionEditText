package ir.naeimsafaee.partitionedittext;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class PartitionEditText extends LinearLayout {

    private final int length = 4;
    private final int edit_text_length = 1;
    private EditText[] editTexts;
    private final int[] backgrounds = {
        R.drawable.edit_text_1_radius,
        R.drawable.edit_text_2_radius,
        R.drawable.edit_text_2_radius,
        R.drawable.edit_text_1_rotate_radius,
    };
    int margin = dpToPx(4);
    int box_width = dpToPx(50);

    private OnCompleteListener onCompleteListener;

    public PartitionEditText(Context context) {
        super(context);
        add_edit_texts(context);
    }

    public PartitionEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        add_edit_texts(context);
    }

    public PartitionEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        add_edit_texts(context);
    }

    private void add_edit_texts(Context context) {

        editTexts = new EditText[length];

        for (int i = 0; i < length; i++) {

            EditText et = new EditText(context);

            LayoutParams layoutParams = new LayoutParams(
                    edit_text_length * box_width,
                    LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(margin, margin, margin, margin);
            et.setLayoutParams(layoutParams);

            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(edit_text_length)});
            et.setGravity(Gravity.CENTER);
            et.setMaxLines(1);
            et.setSingleLine();
            et.setTextColor(context.getResources().getColor(R.color.white));
            et.setBackgroundResource(backgrounds[i]);
            et.setFocusableInTouchMode(true);

            et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

            et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            int finalI = i;
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(et.getText().toString().length() == edit_text_length){

                        if(onCompleteListener != null)
                            onCompleteListener.onCompleteOneStepListener(et.getText().toString() , finalI);

                        if(finalI < length - 1){
//                            editTexts[finalI].clearFocus();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    editTexts[finalI + 1].requestFocus();
                                }
                            },300);

                        } else {

                            StringBuilder text = new StringBuilder();
                            for (int j = 0; j < length; j++) {
                                text.append(editTexts[j].getText().toString());
                            }

                            if(onCompleteListener != null)
                                onCompleteListener.onCompleteListener(text.toString());
                        }
                    }
                }
            });

            editTexts[i] = et;
            addView(et);
        }

    }

    public interface OnCompleteListener {
        void onCompleteListener(String text);
        void onCompleteOneStepListener(String text , int position);
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public int dpToPx(float dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

}
