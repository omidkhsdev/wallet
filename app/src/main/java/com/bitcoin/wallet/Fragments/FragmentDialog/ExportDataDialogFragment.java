package com.bitcoin.wallet.Fragments.FragmentDialog;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExportDataDialogFragment extends DialogFragment{

    private TextInputEditText edPrivateKey;
    private Button buttonCopy,buttonDissmiss;
    private SharedManager sharedManager;
    public ExportDataDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_export_data_dialog, container, false);
        init(view);
        edPrivateKey.setText(sharedManager.getData(Constants.KEY_PRIVATE_KEY));
        buttonCopy.setOnClickListener(v-> copyPrivateKey());
        buttonDissmiss.setOnClickListener(v-> this.dismiss());
        return view;
    }

    private void copyPrivateKey() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(Constants.COPY_WALLET_ADDRESS, edPrivateKey.getText());
        clipboard.setPrimaryClip(clip);
        Toasty.info(Objects.requireNonNull(getActivity()),getString(R.string.toast_copy_private_key)).show();
    }

    private void init(View view){

        Rect displayRectangle = new Rect();
        Window window = getDialog().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        view.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
        view.setMinimumHeight((int) (displayRectangle.height() * 0.6f));

        edPrivateKey=view.findViewById(R.id.fragment_dialog_edit_text_export_wallet_private_key);
        buttonCopy=view.findViewById(R.id.fragment_dialog_import_wallet_btn_confirm);
        buttonDissmiss=view.findViewById(R.id.fragment_dialog_import_wallet_btn_dismiss);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));

    }

}
