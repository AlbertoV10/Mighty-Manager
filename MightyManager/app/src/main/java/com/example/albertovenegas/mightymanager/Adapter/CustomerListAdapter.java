package com.example.albertovenegas.mightymanager.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.customerListHolder> implements Filterable {
    private List<Customer> customerList = new ArrayList<>();
    private OnItemClickListener listener;
    private List<Customer> allCustomers;

    @NonNull
    @Override
    public customerListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_list_item, viewGroup, false);
        return new customerListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull customerListHolder customerListHolder, int i) {
        Customer currentCustomer = customerList.get(i);
        customerListHolder.cName.setText(currentCustomer.getCustomerName());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
        allCustomers = new ArrayList<>(customerList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Customer> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allCustomers);
            }
            else {
                String searchPattern = constraint.toString().toLowerCase().trim();

                for (Customer customer : allCustomers) {
                    if (customer.getCustomerName().toLowerCase().contains(searchPattern)) {
                        filteredList.add(customer);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            customerList.clear();
            customerList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class customerListHolder extends RecyclerView.ViewHolder {
        private TextView cName;

        public customerListHolder(@NonNull View itemView) {
            super(itemView);
            cName = itemView.findViewById(R.id.customer_list_item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(customerList.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Customer customer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
