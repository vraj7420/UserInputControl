package com.example.bookliabray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> implements Filterable {
    ArrayList<Books> bookListFulll;
    ArrayList<Books>  bookList;
    Context ctx;
    ArrayList<String> bookNameAdapterFull;

    public RecyclerViewAdapter(ArrayList<Books> bookList, Context ctx) {
        this.bookList = bookList;
        this.ctx = ctx;
        bookListFulll=new ArrayList<>(bookList);
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater recyclerInflater=LayoutInflater.from(ctx);
        View recyclerView=recyclerInflater.inflate(R.layout.layout,null);
        return new RecyclerViewHolder(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {

    Books  booktemp=bookList.get(position);
    holder.t1.setText(booktemp.getBookNames());
    holder.t2.setText(booktemp.getBookAuthorNames());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           ArrayList<Books>  filterArrayList=new ArrayList<>();
           if (constraint.toString().isEmpty()) {
               filterArrayList.addAll(bookListFulll);

           }else {
               for(Books bookName:bookListFulll){
                   if(bookName.getBookNames().toLowerCase().contains(constraint.toString().toLowerCase()) || bookName.getBookAuthorNames().toLowerCase().contains(constraint.toString().toLowerCase())){
                       filterArrayList.add(bookName);
                   }
               }

           }
           FilterResults filterResults=new FilterResults();
           filterResults.values=filterArrayList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
          bookList.clear();
          bookList.addAll((ArrayList<Books>)results.values);
          notifyDataSetChanged();
        }
    };

    Filter spinnerFilterFunctional=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
        ArrayList<Books> filterArrayListFunctional=new ArrayList<>();
        if(constraint.toString().contains("All")) {
            filterArrayListFunctional.addAll(bookListFulll);

        }
        else {
            for (Books booklist:bookListFulll){
                if(booklist.getBookType().toLowerCase().contains(constraint.toString().toLowerCase())){
                    filterArrayListFunctional.add(booklist);
                }
            }

        }
            FilterResults filterResultsGerne=new FilterResults();
            filterResultsGerne.values=filterArrayListFunctional;
            return filterResultsGerne;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bookList.clear();
            bookList.addAll((Collection<? extends Books>) results.values);
            notifyDataSetChanged();

        }
    };



    Filter spinnerFilterGenre=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Books> filterTemp=new ArrayList<>();
            if(constraint.toString().contains("All"))
            {
                filterTemp.addAll(bookListFulll);
            }
            else {
                for(Books bookName:bookListFulll){
                    if(bookName.getBookGenre().toLowerCase().equals(constraint.toString().toLowerCase())){
                        filterTemp.add(bookName);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterTemp;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bookList.clear();
            bookList.addAll((ArrayList<Books>)results.values);
            notifyDataSetChanged();
        }
    };
    public void sortDataByBookNameAndDate() {
        bookList.sort(new Comparator<Books>() {
            @Override
            public int compare(Books o1, Books o2) {
                if (o1.getBookNames().compareToIgnoreCase(o2.getBookNames()) == 0) {
                    return o1.getBookLaunchDate().compareToIgnoreCase(o2.getBookLaunchDate());
                }
                return o1.getBookNames().compareToIgnoreCase(o2.getBookNames());

            }
        });
        notifyDataSetChanged();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView t1,t2;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.tvBookName);
            t2=(TextView)itemView.findViewById(R.id.tvBookAuthorName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent i=new Intent(ctx,BookDetails.class);
            Books temp=bookList.get(position);

            String name=temp.getBookNames();
            String author=temp.getBookAuthorNames();
            Toast.makeText(ctx, name, Toast.LENGTH_SHORT).show();
            i.putExtra("book",name);
            i.putExtra("author",author);
            ctx.startActivity(i);
        }
    }
}
