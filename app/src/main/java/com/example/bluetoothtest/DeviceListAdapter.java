package com.example.bluetoothtest;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder> {
    public LinkedList<BluetoothDevice> mDevices;
    private DeviceOnClickListener mDeviceOnClickListener;

    public DeviceListAdapter(LinkedList<BluetoothDevice> mDevices, DeviceOnClickListener mDeviceOnClickListener) {
        this.mDevices = mDevices;
        // This is for the constructor of the view holder
        this.mDeviceOnClickListener = mDeviceOnClickListener;
    }

    // Subclass that uses the default RecyclerView view holder. Need this as parameterized for super class
    public class DeviceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name;
        TextView addr;
        DeviceOnClickListener deviceOnClickListener;
        //This will initiate the view. I need to map my variables in the view to the xml components
        public DeviceListViewHolder(@NonNull View itemView, DeviceOnClickListener deviceOnClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.device_name_view);
            addr = itemView.findViewById(R.id.device_address_view);

            this.deviceOnClickListener = deviceOnClickListener;

            // This works becuase it implements onClickListener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // This will pass the position of the click to the On Click method
            v.setBackgroundColor(Color.RED);
            deviceOnClickListener.onDeviceClick(getAdapterPosition());
        }
    }

    // Interface to set up item on click listener
    public interface DeviceOnClickListener {
        void onDeviceClick(int position);
    }

    @Override
    public DeviceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This line is getting the layout file and converting it to a View object
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_device_adapter, null);
        return new DeviceListViewHolder(itemView, mDeviceOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceListViewHolder holder, int position) {
        //Set the text views in the holder to the device in that position of the array
        holder.name.setText(mDevices.get(position).getName());
        holder.addr.setText(mDevices.get(position).getAddress());
    }



    @Override
    public int getItemCount() {
        return mDevices.size();
    }



}