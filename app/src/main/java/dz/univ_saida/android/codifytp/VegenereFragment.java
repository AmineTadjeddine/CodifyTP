package dz.univ_saida.android.codifytp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class VegenereFragment extends Fragment {


    private static final char[] charactersArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};
    private final static char[][] charactersTable = new char[26][26];
    private Button codifyButton;
    private EditText originalTextToCode;
    private EditText codingKey;
    private TextView codedText;
    private Button decodifyButton;
    private EditText originalTextToDecode;
    private EditText decodingKey;
    private TextView decodedText;

    public VegenereFragment() {
        super();
        // Required empty public constructor
    }

    public static void setCharactersTable() {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                charactersTable[i][j] = charactersArray[(j + i) % 26];
            }
        }
    }

    private static char codify(char textCharacter, char keyCharacter) {
        int i = 0;
        int j = 0;
        boolean textCharacterFound = false;
        boolean keyCharacterFound = false;

        do {
            if (keyCharacter == charactersArray[i]) {
                keyCharacterFound = true;
            } else i++;

        } while (!keyCharacterFound && i < charactersArray.length);

        do {
            if (textCharacter == charactersArray[j]) {
                textCharacterFound = true;
            } else j++;

        } while (!textCharacterFound && j < charactersArray.length);

        if (textCharacterFound && keyCharacterFound) {
            return charactersTable[i][j];
        } else {
            return '\u0000';
        }
    }

    private static char decodify(char codedCharacter, char keyCharacter) {
        int i = 0;
        int j = 0;
        boolean codedCharacterFound = false;
        boolean keyCharacterFound = false;

        do {
            if (keyCharacter == charactersArray[i]) {
                keyCharacterFound = true;
            } else i++;

        } while (!keyCharacterFound && i < charactersArray.length);


        do {
            if (charactersTable[i][j] == codedCharacter) {
                codedCharacterFound = true;
            } else j++;
        } while (!codedCharacterFound && j < charactersArray.length);

        if (codedCharacterFound && keyCharacterFound) {
            return charactersArray[j];
        } else {
            return '\u0000';
        }
    }

    private static String encrypt(String text, String key) {
        String codedText = "";
        text = text.toLowerCase();
        key = key.toLowerCase();

        if (text.isEmpty()) {
            return text;
        }
        if (key.isEmpty()) {
            return key;
        }

        for (int index = 0; index < key.length(); index++) {
            char keyCharacter = key.charAt(index);
            boolean validKeyCharacter = false;
            for (int i = 0; i < charactersArray.length && !validKeyCharacter; i++) {
                if (keyCharacter == charactersArray[i]) {
                    validKeyCharacter = true;
                }
            }
            if (!validKeyCharacter) {
                return null;
            }
        }

        for (int index = 0; index < text.length(); index++) {
            char textCharacter = text.charAt(index);
            boolean validTextCharacter = false;
            for (int i = 0; i < charactersArray.length && !validTextCharacter; i++) {
                if (textCharacter == charactersArray[i]) {
                    validTextCharacter = true;
                }
            }

            if (validTextCharacter) {
                codedText += (codify(textCharacter, key.charAt(index % key.length())));
            } else {
                codedText += textCharacter;
            }
        }
        return codedText;
    }

    private static String decrypt(String text, String key) {
        String decodedText = "";
        text = text.toLowerCase();
        key = key.toLowerCase();

        if (text.isEmpty()) {
            return text;
        }
        if (key.isEmpty()) {
            return key;
        }

        for (int index = 0; index < key.length(); index++) {
            char keyCharacter = key.charAt(index);
            boolean validKeyCharacter = false;
            for (int i = 0; i < charactersArray.length && !validKeyCharacter; i++) {
                if (keyCharacter == charactersArray[i]) {
                    validKeyCharacter = true;
                }
            }
            if (!validKeyCharacter) {
                return null;
            }
        }

        for (int index = 0; index < text.length(); index++) {
            char textCharacter = text.charAt(index);
            boolean validTextCharacter = false;
            for (int i = 0; i < charactersArray.length && !validTextCharacter; i++) {
                if (textCharacter == charactersArray[i]) {
                    validTextCharacter = true;
                }
            }

            if (validTextCharacter) {
                decodedText += (decodify(textCharacter, key.charAt(index % key.length())));
            } else {
                decodedText += textCharacter;
            }
        }
        return decodedText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.standered_layout, container, false);

        ((TextView) rootView.findViewById(R.id.algorithm_type)).setText("Vegènere's Algorithm");


        codifyButton = (Button) rootView.findViewById(R.id.codifyButton);
        originalTextToCode = (EditText) rootView.findViewById(R.id.original_to_code);
        codingKey = (EditText) rootView.findViewById(R.id.coding_key);
        codedText = (TextView) rootView.findViewById(R.id.coded_text);
        decodifyButton = (Button) rootView.findViewById(R.id.decodifyButton);
        originalTextToDecode = (EditText) rootView.findViewById(R.id.original_to_decode);
        decodingKey = (EditText) rootView.findViewById(R.id.decoding_key);
        decodedText = (TextView) rootView.findViewById(R.id.decoded_text);

        setCharactersTable();
        codingKey.setInputType(InputType.TYPE_CLASS_TEXT);
        decodingKey.setInputType(InputType.TYPE_CLASS_TEXT);


        codifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!originalTextToCode.getText().toString().isEmpty()) {
                    if (!codingKey.getText().toString().isEmpty()) {
                        codedText.setText(encrypt(originalTextToCode.getText().toString(), codingKey.getText().toString()));
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
                        decodedText.setText(decrypt(originalTextToDecode.getText().toString(), decodingKey.getText().toString()));
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
