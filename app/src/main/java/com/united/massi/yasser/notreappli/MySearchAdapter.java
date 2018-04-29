//package com.united.massi.yasser.notreappli;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.widget.ArrayAdapter;
//import android.widget.Filter;
//
//import java.util.List;
//
//public class MySearchAdapter extends ArrayAdapter {
//
//    public MySearchAdapter(@NonNull Context context, int resource, @NonNull List objects) {
//        super(context, resource, objects);
//    }
//    @Override
//    public Filter getFilter() {
//        return nameFilter;
//    }
//
//    Filter nameFilter = new Filter() {
//        @Override
//        public String convertResultToString(Object resultValue) {
//            String str = ((Customer)(resultValue)).getName();
//            return str;
//        }
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if(constraint != null) {
//                suggestions.clear();
//                for (Customer customer : itemsAll) {
//                    if(customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
//                        suggestions.add(customer);
//                    }
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            ArrayList<Customer> filteredList = (ArrayList<Customer>) results.values;
//            if(results != null && results.count > 0) {
//                clear();
//                for (Customer c : filteredList) {
//                    add(c);
//                }
//                notifyDataSetChanged();
//            }
//        }
//    };
//
//}
