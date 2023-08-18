package com.dovantuan.onthi.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dovantuan.onthi.DAO.DsDAO;
import com.dovantuan.onthi.DTO.DSDTO;
import com.dovantuan.onthi.MainActivity;
import com.dovantuan.onthi.Notify.NotifyConfig;
import com.dovantuan.onthi.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterDs extends RecyclerView.Adapter<AdapterDs.ViewHolderDs>{
    Context context;
    List<DSDTO> list_ds;

    DsDAO dsDAO;

    public AdapterDs(ArrayList<DSDTO> list_ds, Context context) {
        this.list_ds = list_ds;
        this.context = context;
        dsDAO = new DsDAO(context);
    }

    @NonNull
    @Override
    public AdapterDs.ViewHolderDs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_lv_ds, parent, false);

        ViewHolderDs holder = new ViewHolderDs(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDs.ViewHolderDs holder, int position) {
        DSDTO objDsdto =list_ds.get(position);
        holder.tv_ten.setText(objDsdto.getTenmon());
        holder.tv_ca.setText(objDsdto.getCa());

        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning");
                builder.setIcon(R.drawable.thongbao);
                builder.setCancelable(false);
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");

                builder.setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = list_ds.get(holder.getAdapterPosition()).getId();
                        boolean check = dsDAO.removeRow(id);
                        if (check) {
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            thongbao();
                            list_ds.clear();
                            list_ds = dsDAO.getAll();
                            notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        holder.btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSDTO dsdto = list_ds.get(holder.getAdapterPosition());
                DialogUpdate(dsdto);
            }
        });

        holder.btn_xemchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSDTO dsdto = list_ds.get(holder.getAdapterPosition());
                DialogViewDetail(dsdto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_ds.size();
    }


    public class ViewHolderDs extends RecyclerView.ViewHolder {
        TextView tv_ten,tv_ca;
        ImageView btn_sua,btn_xoa;

        LinearLayout btn_xemchitiet;
        public ViewHolderDs(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.tv_tenmon);
            tv_ca = itemView.findViewById(R.id.tv_ca);
            btn_sua = itemView.findViewById(R.id.btn_sua);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);
            btn_xemchitiet = itemView.findViewById(R.id.btn_view);
        }
    }

    public void DialogUpdate(DSDTO Update) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_update_ds,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText ngaythi = view.findViewById(R.id.ed_ngay_thi_update);
        EditText cathi = view.findViewById(R.id.ed_ca_update);
        EditText phong = view.findViewById(R.id.ed_phong_update);
        EditText tenmon = view.findViewById(R.id.ed_ten_mon_update);

        Button btnUpdate = view.findViewById(R.id.btn_save_update);
        Button btnCancel = view.findViewById(R.id.btn_cancel_update);

        ngaythi.setText(Update.getNgaythi());
        cathi.setText(Update.getCa());
        phong.setText(Update.getPhong());
        tenmon.setText(Update.getTenmon());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngayThi = ngaythi.getText().toString().trim();
                String ca = cathi.getText().toString().trim();
                String phongthi = phong.getText().toString().trim();
                String tenMon = tenmon.getText().toString().trim();

                DSDTO ds = new DSDTO(Update.getId(),ngayThi,ca,phongthi ,tenMon);
                boolean check = dsDAO.updateRow(ds);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    list_ds.clear();
                    list_ds = dsDAO.getAll();
                    notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogViewDetail(DSDTO detail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_xemchitiet, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText ngaythi = view.findViewById(R.id.ed_ngay_thi);
        EditText cathi = view.findViewById(R.id.ed_ca);
        EditText phong = view.findViewById(R.id.ed_phong);
        EditText tenmon = view.findViewById(R.id.ed_ten_mon);

        Button btnCancel = view.findViewById(R.id.btn_cancel);
        // Thêm dòng này để lấy tham chiếu tới nút btn_hienthi


        ngaythi.setText(detail.getNgaythi());
        cathi.setText(detail.getCa());
        phong.setText(detail.getPhong());
        tenmon.setText(detail.getTenmon());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void thongbao(){
        // Khai báo Intent để nhận tương tác khi bấm vào notify
        Intent intentDemo = new Intent(context, MainActivity.class); // Thay MainActivity bằng tên lớp Activity của bạn
        intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentDemo.putExtra("duLieu", "Dữ liệu gửi từ notify vào activity");

        // ***** Tạo Stack để gọi activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intentDemo);

        // Lấy PendingIntent để truyền vào notify
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Ảnh
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.thongbao); // Thay thế R.drawable.logo bằng tên tài nguyên hình ảnh của bạn

        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(context, NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle("Thông báo")
                .setContentText("Bạn vừa xóa một ca thi")
                .setContentIntent(resultPendingIntent) // intent để nhận tương tác

                // Thiết lập cho ảnh to
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(logo).bigLargeIcon(null))
                .setLargeIcon(logo)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .build();

        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 999999);
            return; // Thoát khỏi hàm nếu chưa được cấp quyền
        }
        // Nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // Mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notify = (int) new Date().getTime(); // Lấy chuỗi thời gian là phù hợp
        // Lệnh hiển thị notify
        notificationManagerCompat.notify(id_notify, customNotification);
    }

}
