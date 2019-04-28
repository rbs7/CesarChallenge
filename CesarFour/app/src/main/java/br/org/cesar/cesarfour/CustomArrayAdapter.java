package br.org.cesar.cesarfour;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> mOriginalValues;
    private ArrayList<String> objects;

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isPartialPermutation(String w1, String w2) {
        boolean ok = true;
        int i, cont;
        int[] alpha = new int[26];
        Arrays.fill(alpha, 0);

        if (w1.charAt(0) != w2.charAt(0)) { //first letter check
            ok = false;
        }
        if (w1.length() != w2.length()) { //string length check
            ok = false;
        }
        if (ok) {
            for (i = 0, cont = 0; i < w1.length() && i < w2.length(); i++) {
                if (w1.charAt(i) != w2.charAt(i)) { //letter by letter check
                    cont++;
                    alpha[w1.charAt(i)-'a']++;
                    alpha[w2.charAt(i)-'a']--;
                }
            }
            if (i > 3 && cont > i*2/3) {
                ok = false;
            }
            for (i = 0; i < 26; i++) {  //letter by letter check
                if (alpha[i] != 0) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    private int countTypos (String w1, int i, String w2, int j) {
        if (i >= w1.length()) {
            if (j >= w2.length()) {
                return 0;
            } else {
                return 1 + countTypos(w1, i, w2, j+1);
            }
        } else {
            if (j >= w2.length()) {
                return 1 + countTypos(w1, i+1, w2, j);
            } else {
                if (w1.charAt(i)==w2.charAt(j)) {
                    return countTypos(w1, i+1, w2, j+1);
                } else {
                    return 1 + Math.min(countTypos(w1, i+1, w2, j+1), Math.min(countTypos(w1, i+1, w2, j), countTypos(w1, i, w2, j+1)));
                }
            }
        }
    }


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                objects = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<String> filteredArrList = new ArrayList<String>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<String>(objects);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String item = mOriginalValues.get(i);

                        int typos = countTypos (item.toLowerCase(), 0, constraint.toString(), 0);
                        boolean isPP = isPartialPermutation(item.toLowerCase(), constraint.toString());

                        if (typos == 0 || (typos == 1 ^ isPP)) {
                            filteredArrList.add(item);
                        }
                    }

                    results.count = filteredArrList.size();
                    results.values = filteredArrList;

                }
                return results;
            }
        };
        return filter;
    }

}
