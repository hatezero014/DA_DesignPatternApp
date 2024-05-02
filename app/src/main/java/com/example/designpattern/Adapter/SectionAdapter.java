package com.example.designpattern.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.Section;
import com.example.designpattern.R;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder>{
    public SectionAdapter(Context mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
    }

    private Context mContext;

    private FragmentManager fragmentManager;

    private List<Section> mSection;

    public void setFragmentID(int fragmentID) {
        this.fragmentID = fragmentID;
    }

    private int fragmentID;

    private int selectedPosition = 0;

    public void setData(List<Section> list){
        this.mSection = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section,parent,false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        Section section = mSection.get(position);
        if(section == null){
            return;
        }

        final int x = position;

        holder.tv_section.setSelected(position == selectedPosition);
        holder.tv_section.setText(section.getName());

        holder.tv_section.setOnClickListener(v -> {
            selectedPosition = x;
            notifyDataSetChanged();


//            DescriptionFragment descriptionFragment = new DescriptionFragment();
//            ProsConsFragment prosConsFragment = new ProsConsFragment();
//            CaseFragment caseFragment = new CaseFragment();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//            if(section.getName().equals("description")){
//                transaction.replace(fragmentID, descriptionFragment);
//            }
//            else if(section.getName().equals("pros & cons"))
//                transaction.replace(fragmentID, prosConsFragment);
//            else if(section.getName().equals("case")){
//                transaction.replace(fragmentID, caseFragment);
//            }
//
//            transaction.addToBackStack(null);
//            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        if(mSection != null)
            return mSection.size();
        return 0;
    }



    public class SectionViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_section;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_section = itemView.findViewById(R.id.tv_section);
        }
    }
}