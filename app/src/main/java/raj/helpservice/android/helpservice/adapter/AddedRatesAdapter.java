package raj.helpservice.android.helpservice.adapter;


import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import raj.helpservice.android.helpservice.R;
import raj.helpservice.android.helpservice.data.AddedRatesModel;


/**
 *
 */


public class AddedRatesAdapter extends RecyclerView.Adapter<AddedRatesAdapter.ViewHolder> {
	private ArrayList<AddedRatesModel> mDataset = new ArrayList<>();
	private OnItemClickListener mClickListener;
	public interface OnItemClickListener {
		void onItemClick(AddedRatesModel item, int position);
		void onRemoveClick(AddedRatesModel item, int position);
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView serviceType;
		public TextView serviceTime;
		public TextView serviceRate;
		public TextView serviceCharges;
		public TextView discountAmount;
		public ImageView dots;

		public ViewHolder(View v) {
			super(v);
			serviceType = v.findViewById(R.id.service_type);
			serviceTime = v.findViewById(R.id.service_time);
			serviceRate = v.findViewById(R.id.service_rate);
			serviceCharges = v.findViewById(R.id.service_charges);
			discountAmount = v.findViewById(R.id.discount_amount);
			dots = v.findViewById(R.id.dot_view);
			dots.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			PopupMenu popup = new PopupMenu(v.getContext(), itemView);
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
						case R.id.edit_pop_up:
							mClickListener.onItemClick(mDataset.get(getAdapterPosition()),getAdapterPosition());
						case R.id.remove_pop_up:
							mClickListener.onRemoveClick(mDataset.get(getAdapterPosition()),getAdapterPosition());
							return true;
						default:
							return false;
					}
				}
			});
			popup.inflate(R.menu.pop_up_menu);
			popup.setGravity(Gravity.RIGHT);
			popup.show();


		}
	}



	public AddedRatesAdapter(OnItemClickListener clickListener) {
		mClickListener = clickListener;
	}

	@Override
	public AddedRatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.requested_service_item, parent, false);

		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final AddedRatesModel item = mDataset.get(position);
		holder.serviceCharges.setText("Service charges: "+item.serviceCharges);
		holder.serviceTime.setText("Service time: " +item.serviceName);
		holder.serviceRate.setText("Rate: "+item.serviceRate);
		holder.serviceType.setText(item.serviceType);
		holder.discountAmount.setText("Discount amount: "+item.discountAmount);

	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public void changeValues(ArrayList<AddedRatesModel> models){
		mDataset.clear();
		mDataset.addAll(models);
		notifyDataSetChanged();
	}


}
