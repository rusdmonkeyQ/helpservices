package raj.helpservice.android.helpservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import raj.helpservice.android.helpservice.R;
import raj.helpservice.android.helpservice.data.DocumentModel;


/**
 *
 */

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(DocumentModel item, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView documentName;
        public TextView documentType;

        public ViewHolder(View v) {
            super(v);
            documentName = v.findViewById(R.id.document_name);
            documentType = v.findViewById(R.id.document_type);
        }
    }

    private ArrayList<DocumentModel> mDataset = new ArrayList<>();
    private DocumentsAdapter.OnItemClickListener mClickListener;

    public DocumentsAdapter(DocumentsAdapter.OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public DocumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.documents_view, parent, false);

        return new DocumentsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DocumentsAdapter.ViewHolder holder, final int position) {
        final DocumentModel item = mDataset.get(position);

//        if (item.isAadhar) {
//            holder.mButton.setVisibility(View.INVISIBLE);
//        } else {
//            holder.mButton.setText("Delete");
//            holder.mButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mClickListener != null) {
//                        mClickListener.onItemClick(item, position);
//                    }
//                }
//            });
//        }
        holder.documentType.setText(item.documentName);
    }

    private String getDescription(DocumentModel item) {
        String text = "";
        if (item == null) {
            return text;
        }
        if (!TextUtils.isEmpty(item.documentName)) {
            if (!text.isEmpty()) text += "\n";
            text += item.documentName;
        }
        if (!TextUtils.isEmpty(item.documentNo)) {
            if (!text.isEmpty()) text += "\n";
            text += "Documents No: " + item.documentNo;
        }
        if (!TextUtils.isEmpty(item.docVerified)) {
            if (!text.isEmpty()) text += "\n";
            text += "Document Verified: " + item.docVerified;
        }
        return text;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void changeValues(ArrayList<DocumentModel> documentModels){
    	mDataset.clear();
    	mDataset.addAll(documentModels);
    	notifyDataSetChanged();
	}

}
