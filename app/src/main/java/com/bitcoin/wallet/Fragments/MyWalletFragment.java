package com.bitcoin.wallet.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.Fragments.FragmentDialog.ExportDataDialogFragment;
import com.bitcoin.wallet.Fragments.FragmentDialog.TransferDialogFragment;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.content.Context.CLIPBOARD_SERVICE;

public class MyWalletFragment extends Fragment {
    private TextInputEditText edWalletAddress;
    private Button buttonCopyWalletAddress, buttonTransfer,buttonExportWalletData;
    private ImageView imageQrCode;
    private SharedManager sharedManager;

    public MyWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!Constants.TARGET_PUBLIC_KEY_SCAN.equals("")) {
            Toasty.info(Objects.requireNonNull(getActivity()), Constants.TARGET_PUBLIC_KEY_SCAN).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_wallet, container, false);
        init(v);
        edWalletAddress.setText(sharedManager.getData(Constants.KEY_WALLET_ADDRESS));
        buttonCopyWalletAddress.setOnClickListener(view -> copyWalletAddress());
        buttonTransfer.setOnClickListener(view -> transfer());
        buttonExportWalletData.setOnClickListener(view -> showExportDialogFragment());
        Glide
                .with(Objects.requireNonNull(getActivity()))
                .load(generateQRCode(sharedManager.getData(Constants.KEY_WALLET_ADDRESS)))
                .fitCenter()
                .into(imageQrCode);

        return v;
    }

    private void showExportDialogFragment() {
        assert getFragmentManager() != null;
        ExportDataDialogFragment dialogExchange = new ExportDataDialogFragment();
        dialogExchange.show(getFragmentManager(), "dialog_fragment_export_data");
    }

    public MyWalletFragment newInstance() {

        Bundle args = new Bundle();
        MyWalletFragment fragment = new MyWalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void init(View view) {
        edWalletAddress = view.findViewById(R.id.fragment_wallet_tiet_public_key);
        buttonCopyWalletAddress = view.findViewById(R.id.fragment_wallet_btn_copy_pub_key);
        buttonTransfer = view.findViewById(R.id.fragment_wallet_btn_scan_qrcode);
        buttonExportWalletData = view.findViewById(R.id.fragment_wallet_btn_export);
        imageQrCode = view.findViewById(R.id.fragment_wallet_imgv_qrcode);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));

    }

    private void copyWalletAddress() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(Constants.COPY_WALLET_ADDRESS, edWalletAddress.getText());
        clipboard.setPrimaryClip(clip);
        Toasty.info(Objects.requireNonNull(getActivity()), getString(R.string.toast_copy_wallet_address)).show();
    }

    //transfer
    private void transfer(){
        TransferDialogFragment dialogExchange = new TransferDialogFragment();
        dialogExchange.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "transfer");
    }
    //generate qr code image
    private Bitmap generateQRCode(String walletAddress) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(walletAddress, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

    }
}
