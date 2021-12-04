package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.util.NumericUtil;

public class MoneySettingFragment<onRequestPermissionsResult> extends Fragment implements OnSaveListener<Auctions> {

    private Auctions auctions;
    private Auctioneer auctioneer;
    private RadioButton buttonFixedMoney;
    private RadioButton buttonGetMoneyByAge;
    private EditText textFixedMoney;
    private EditText textGetMoneyByAge;
    private ImageButton nextStepButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.money_setting, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String jsonAuctioneer = bundle.getString(System.KEY_AUCTIONEER);
        auctioneer = JsonUtils.parseJson(jsonAuctioneer, Auctioneer.class);
        String jsonAuction = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(jsonAuction, Auctions.class);

        buttonFixedMoney = view.findViewById(R.id.rdb_fix_number_money);
        buttonGetMoneyByAge = view.findViewById(R.id.rdb_get_money_by_age);
        textFixedMoney = view.findViewById(R.id.txt_fix_number_money);
        textGetMoneyByAge = view.findViewById(R.id.txt_get_money_by_age);
        nextStepButton = view.findViewById(R.id.btn_money_setting_next_step);

        buttonFixedMoney.setChecked(true);
        textFixedMoney.setEnabled(true);
        buttonGetMoneyByAge.setChecked(false);
        textGetMoneyByAge.setEnabled(false);

        buttonFixedMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonOfFixedMoney();
            }
        });

        buttonGetMoneyByAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonOfGetMoneyByAge();
            }
        });

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonFixedMoney.isChecked() && !buttonGetMoneyByAge.isChecked()) {
                    Toast.makeText(getContext(), R.string.msg_money_setting_invalid_selected, Toast.LENGTH_LONG);
                    nextStepButton.setBackgroundResource(android.R.drawable.btn_default);
                    return;
                }

                Logger.i("nestStepButton 1");

                // get money setting info
                int moneyCreationWay = MoneyCreationWay.FIX_AMOUNT;
                String money = textFixedMoney.getText().toString();
                String perAge = textGetMoneyByAge.getText().toString();
                if (buttonFixedMoney.isChecked() == false) moneyCreationWay = MoneyCreationWay.RETRIEVE_BY_AGE;

                if (moneyCreationWay == MoneyCreationWay.FIX_AMOUNT) {
                    Logger.i("nestStepButton 21");
                    if (NumericUtil.isNumeric(money) == false) {
                        Logger.i("nestStepButton 22");
                        String patton = getString(R.string.amount_must_be_digital);
                        String msg = String.format(patton, "money amount");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        return;
                    } else if (NumericUtil.greaterThanZero(money) == false) {
                        Logger.i("nestStepButton 23");
                        String patton = getString(R.string.must_greater_than_zero);
                        String msg = String.format(patton, "money amount");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Logger.i("nestStepButton 31");
                    if (NumericUtil.isNumeric(perAge) == false) {
                        Logger.i("nestStepButton 32");
                        String patton = getString(R.string.amount_must_be_digital);
                        String msg = String.format(patton, "money per month");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        return;
                    } else if (NumericUtil.greaterThanZero(perAge) == false) {
                        Logger.i("nestStepButton 33");
                        String patton = getString(R.string.must_greater_than_zero);
                        String msg = String.format(patton, "money per month");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Logger.i("nestStepButton 4");

                // save auctions and navigate to next fragment when callback, see onSaved method.
                long amount = Long.parseLong(money);
                int pAge = Integer.parseInt(perAge);

                int auctioneerId = auctioneer.getId();
                if (auctioneerId <= 0) return;

                String auctioneerName = auctioneer.getUsername();
                if (auctioneerName == null || auctioneerName.length() == 0) return;

                OnSaveListener listener = MoneySettingFragment.this;
                BeanManager.auctionsRepository.saveAuctions(auctions.getId(), auctioneerId, auctioneerName, moneyCreationWay, amount, pAge, listener);

            }
        });

        this.fillEditTextIfPossible();

    }

    private void clickButtonOfFixedMoney() {
        textFixedMoney.setEnabled(true);
        textGetMoneyByAge.setEnabled(false);
        buttonGetMoneyByAge.setChecked(false);
        nextStepButton.setBackgroundColor(getResources().getColor(R.color.list_item_background_color));
    }

    private void clickButtonOfGetMoneyByAge() {
        textGetMoneyByAge.setEnabled(true);
        textFixedMoney.setEnabled(false);
        buttonFixedMoney.setChecked(false);
        nextStepButton.setBackgroundColor(getResources().getColor(R.color.list_item_background_color));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_money_setting_fragment_title);
    }

    @Override
    public void onSaved(Auctions auctions, long id) {
        Bundle bundle = new Bundle();
        auctions.setId((int)id);
        bundle.putString(System.KEY_AUCTION, auctions.toJson());

        int fragmentId = R.id.action_moneySettingFragment_to_bidderListFragment;
        NavHostFragment.findNavController(MoneySettingFragment.this).navigate(fragmentId, bundle);
    }

    private void fillEditTextIfPossible() {
        if (auctions.getId() <= 0) return;
        if (auctions.getMoneyCreationWay() == MoneyCreationWay.FIX_AMOUNT) {
            textFixedMoney.setText(String.valueOf(auctions.getMoney()));
            buttonFixedMoney.setChecked(true);
            clickButtonOfFixedMoney();
        } else {
            textGetMoneyByAge.setText(String.valueOf(auctions.getAmountPerAge()));
            buttonGetMoneyByAge.setChecked(true);
            clickButtonOfGetMoneyByAge();
        }
    }
}
