package cs4330.cs.utep.tuitioncalculationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tuitionTxt, feesTxt, totalTxt, creditHoursTxt;
    private RadioGroup resCheckGrp, gradCheckGrp;
    private RadioButton gradRB, underGradRB, resRB, nonResRB;
    private final double csFee = 1023.9;
    final DecimalFormat numFormat = new DecimalFormat(".##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpComponents();


        creditHoursTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });

        gradCheckGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculate();
            }
        });

        resCheckGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculate();
            }
        });

    }

    public boolean isNumber(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            if(!Character.isDigit(charArray[i])) {
                return false;
            }
        }
        return true;
    }


    /**
     * Defines all components (textviews and buttons) used by the app.
     * */
    private void setUpComponents() {
        tuitionTxt = findViewById(R.id.tuition);
        feesTxt = findViewById(R.id.fees);
        totalTxt = findViewById(R.id.total);
        creditHoursTxt = findViewById(R.id.creditHours);
        resCheckGrp = (RadioGroup) findViewById(R.id.resCheck);
        gradCheckGrp = (RadioGroup) findViewById(R.id.gradCheck);

        gradRB = findViewById(R.id.graduate);
        underGradRB = findViewById(R.id.underGraduate);
        resRB = findViewById(R.id.resident);
        nonResRB = findViewById(R.id.nonResident);
    }

    private void calculate() {
        if(!creditHoursTxt.getText().toString().matches("")) {
            int value = Integer.parseInt(creditHoursTxt.getText().toString());
            if (gradCheckGrp.getCheckedRadioButtonId() != -1 && resCheckGrp.getCheckedRadioButtonId() != -1) {
                if (value > 0 && value <= 24) {
                    double perCredit = 0;
                    if (underGradRB.isChecked()) {
                        if (resRB.isChecked()) {
                            perCredit = 336.46;
                        } else {
                            perCredit = 534.62;
                        }
                    } else {
                        if (resRB.isChecked()) {
                            perCredit = 455.13;
                        } else {
                            perCredit = 988.90;
                        }
                    }
                    tuitionTxt.setText("Tuition: $" + roundNumber(perCredit * value));
                    feesTxt.setText("Fees: $" + csFee);
                    totalTxt.setText("Total: $" + roundNumber((perCredit * value) + csFee));
                    return;
                }
            }
        }
        tuitionTxt.setText("Tuition: ");
        feesTxt.setText("Fees: ");
        totalTxt.setText("Total: ");
    }

    private double roundNumber(double num) {
        return Double.parseDouble(numFormat.format(num));
    }
}
