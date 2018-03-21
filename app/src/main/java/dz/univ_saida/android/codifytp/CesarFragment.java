package dz.univ_saida.android.codifytp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class CesarFragment extends Fragment {

    private static final char[] charactersArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};
    private Button codifyButton;
    private EditText originalTextToCode;
    private EditText codingKey;
    private TextView codedText;
    private Button decodifyButton;
    private EditText originalTextToDecode;
    private EditText decodingKey;
    private TextView decodedText;

    public CesarFragment() {
        // Required empty public constructor
    }

    /**
     * function that takes the character and returns its index in the array
     *
     * @param character that we're searching for its index in the array
     * @return the index
     */
    private static int codify(char character) {
        int i = 0;
        boolean characterFound = false;

        do {
            if (character == charactersArray[i]) {
                characterFound = true;
            } else i++;

        } while (!characterFound && i < charactersArray.length);

        if (characterFound) {
            return i;
        } else {
            return -1;
        }
    }

    private static char decodify(int aNumber) {
        return charactersArray[(((aNumber % 26) + 26) % 26)];
    }


    private static String encrypt(String text, int key) {

        String codedText = "";
        text = text.toLowerCase();

        if (text.isEmpty()) {
            return text;
        }

        for (int index = 0; index < text.length(); index++) {
            char aChar = text.charAt(index);
            boolean validChar = false;
            for (int i = 0; i < charactersArray.length && !validChar; i++) {
                if (aChar == charactersArray[i]) {
                    validChar = true;
                }
            }
            if (validChar) {
                codedText += decodify((((codify(aChar) + key) % 26) + 26) % 26);
            } else {
                codedText += aChar;
            }
        }
        return codedText;
    }

    private static String decrypt(String cryptedText, int key) {

        String decodedText = "";
        cryptedText = cryptedText.toLowerCase();

        if (cryptedText.isEmpty()) {
            return cryptedText;
        }

        for (int index = 0; index < cryptedText.length(); index++) {
            char aChar = cryptedText.charAt(index);
            boolean validChar = false;
            for (int i = 0; i < charactersArray.length && !validChar; i++) {
                if (aChar == charactersArray[i]) {
                    validChar = true;
                }
            }
            if (validChar) {
                decodedText += decodify((((codify(aChar) - key) % 26) + 26) % 26);
            } else {
                decodedText += aChar;
            }
        }
        return decodedText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.standered_layout, container, false);

        ((TextView) rootView.findViewById(R.id.algorithm_type)).setText("Cesar's Algorithm");

        codifyButton = (Button) rootView.findViewById(R.id.codifyButton);
        originalTextToCode = (EditText) rootView.findViewById(R.id.original_to_code);
        codingKey = (EditText) rootView.findViewById(R.id.coding_key);
        codedText = (TextView) rootView.findViewById(R.id.coded_text);
        decodifyButton = (Button) rootView.findViewById(R.id.decodifyButton);
        originalTextToDecode = (EditText) rootView.findViewById(R.id.original_to_decode);
        decodingKey = (EditText) rootView.findViewById(R.id.decoding_key);
        decodedText = (TextView) rootView.findViewById(R.id.decoded_text);

        codingKey.setInputType(InputType.TYPE_CLASS_NUMBER);
        decodingKey.setInputType(InputType.TYPE_CLASS_NUMBER);


        codifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!originalTextToCode.getText().toString().isEmpty()) {
                    if (!codingKey.getText().toString().isEmpty()) {
                        codedText.setText(encrypt(originalTextToCode.getText().toString(), Integer.parseInt(codingKey.getText().toString())));
                    } else {
                        Toast.makeText(getActivity(), "Key is empty!", Toast.LENGTH_SHORT).show();
                        codedText.setText("");
                    }
                } else {
                    Toast.makeText(getActivity(), "Text To Code is empty!", Toast.LENGTH_SHORT).show();
                    codedText.setText("");
                }
            }
        });

        decodifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!originalTextToDecode.getText().toString().isEmpty()) {
                    if (!decodingKey.getText().toString().isEmpty()) {
                        decodedText.setText(decrypt(originalTextToDecode.getText().toString(), Integer.parseInt(decodingKey.getText().toString())));
                    } else {
                        Toast.makeText(getActivity(), "Key is empty!", Toast.LENGTH_SHORT).show();
                        decodedText.setText("");
                    }
                } else {
                    Toast.makeText(getActivity(), "Text to deCode is empty!", Toast.LENGTH_SHORT).show();
                    decodedText.setText("");
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


}
