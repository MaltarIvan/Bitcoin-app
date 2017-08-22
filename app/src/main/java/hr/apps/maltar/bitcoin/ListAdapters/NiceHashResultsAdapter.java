package hr.apps.maltar.bitcoin.ListAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hr.apps.maltar.bitcoin.R;
import hr.apps.maltar.bitcoin.entities.niceHash.Current;

/**
 * Created by Maltar on 22.8.2017..
 */

public class NiceHashResultsAdapter extends ArrayAdapter<Current> {
    public NiceHashResultsAdapter(Context context, List<Current> currents) {
        super(context, 0, currents);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.nice_hash_result_currents_list_item, parent, false);
        }

        Current currentCurrent = getItem(position);

        TextView algoName = (TextView) listItemView.findViewById(R.id.currents_list_item_algo_name);
        TextView algoSufix = (TextView) listItemView.findViewById(R.id.currents_list_item_algo_sufix);
        TextView algoNumber = (TextView) listItemView.findViewById(R.id.currents_list_item_algo_number);
        TextView algoProfitability = (TextView) listItemView.findViewById(R.id.currents_list_item_algo_profitability);

        algoName.setText(currentCurrent.getName());
        algoSufix.setText(currentCurrent.getSuffix());
        algoNumber.setText(currentCurrent.getAlgo());
        algoProfitability.setText(currentCurrent.getProfitability());

        return listItemView;
    }
}
