package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Quiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Quiz extends Fragment {
    DBHelper db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Quiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Quiz.
     */
    // TODO: Rename and change types and number of parameters
    public static Quiz newInstance(String param1, String param2) {
        Quiz fragment = new Quiz();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = new DBHelper(getContext());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View view; // Added member variable
    private TextView letterTextView;
    private RadioGroup optionsRadioGroup;
    private Button nextButton;
    private TextView resultTextView;

    private String[] letters = { "h", "m", "f", "q", "y" };
    private String[] outcomes = { "Sky", "Grass", "Root" };

    private String [] answers = {"Sky", "Grass", "Sky", "Root", "Root"};

    private int currentLetterIndex = 0;
    private int correctAnswers = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz, container, false);

        letterTextView = view.findViewById(R.id.letterTextView);
        optionsRadioGroup = view.findViewById(R.id.optionsRadioGroup);
        nextButton = view.findViewById(R.id.nextButton);
        resultTextView = view.findViewById(R.id.resultTextView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLetterIndex < letters.length) {
                    if (checkAnswer(currentLetterIndex)){
                        currentLetterIndex++;
                        if (currentLetterIndex < letters.length) {
                            showNextLetter();
                        } else {
                            showResult();
                        }
                    }

                }
            }
        });

        // Show the first letter
        showNextLetter();

        return view;
    }

    private void showNextLetter() {
        letterTextView.setText(letters[currentLetterIndex]);
        optionsRadioGroup.clearCheck();
        resultTextView.setVisibility(View.GONE);
    }

    private boolean checkAnswer(int currentLetterIndex) {
        int checkedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();
        if (view == null){
            return false;
        }
        RadioButton selectedOption = view.findViewById(checkedRadioButtonId);
        if (selectedOption != null){
            if(selectedOption.getText().toString().equals(answers[currentLetterIndex])){
                correctAnswers++;
            }
            return true;
        }
        else return false;
    }

    private void showResult() {
        letterTextView.setText("");
        optionsRadioGroup.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        resultTextView.setVisibility(View.VISIBLE);
        resultTextView.setText("Game Ended. Correct Answers: " + correctAnswers);
        db.insertResult(new Result("junaid", correctAnswers));
    }

    private int getIndex(String targetValue) {
        for (int i = 0; i < outcomes.length; i++) {
            if (outcomes[i].equals(targetValue)) {
                return i;
            }
        }
        return -1; // Return -1 if the target value is not found in the array
    }
}