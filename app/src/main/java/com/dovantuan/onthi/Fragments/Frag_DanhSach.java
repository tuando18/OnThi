package com.dovantuan.onthi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dovantuan.onthi.Adapter.AdapterDs;
import com.dovantuan.onthi.DAO.DsDAO;
import com.dovantuan.onthi.DTO.DSDTO;
import com.dovantuan.onthi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Frag_DanhSach extends Fragment {
    FloatingActionButton fab;
    RecyclerView rc_ds;
    ArrayList<DSDTO> list_ds = new ArrayList<>();
    DsDAO dsDAO = new DsDAO(getActivity());
    AdapterDs adapterDs;

    EditText ed_ngaythi, ed_ca, ed_phong, ed_tenmon;
    Button btn_cancel, btn_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_danhsach,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc_ds = view.findViewById(R.id.rc_ds);

        dsDAO = new DsDAO(getContext());
        list_ds = dsDAO.getAll();

        adapterDs = new AdapterDs(list_ds, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_ds.setLayoutManager( linearLayoutManager );

        rc_ds.setAdapter(adapterDs);
        //thêm
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogAddLop();
            }
        });

    }

    void ShowDialogAddLop(){
        //định nghĩa dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.layout_dialog_add_ds, null);
        builder.setView(v);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();//khởi tạo dialog

        //tương tác view
        ed_ngaythi = v.findViewById(R.id.ed_ngay_thi);
        ed_ca= v.findViewById(R.id.ed_ca);
        ed_phong = v.findViewById(R.id.ed_phong);
        ed_tenmon = v.findViewById(R.id.ed_ten_mon);

        Button btn_save = v.findViewById(R.id.btn_save);
        Button btn_cancel = v.findViewById(R.id.btn_cancel);

        //đưa dữ liệu lên spiner

        // ...
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayThi = ed_ngaythi.getText().toString().trim();
                String ca = ed_ca.getText().toString().trim();
                String phongthi = ed_phong.getText().toString().trim();
                String tenMon = ed_tenmon.getText().toString().trim();

                if (ngayThi.isEmpty() || ca.isEmpty() || phongthi.isEmpty() || tenMon.isEmpty()) {
                    // Hiển thị thông báo lỗi khi có ô nhập trống
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!ca.matches("[1-6]")) {
                    // Hiển thị thông báo lỗi khi ca thi không nằm trong khoảng từ 1 đến 6
                    Toast.makeText(getContext(), "Ca thi chỉ nhập từ 1-6", Toast.LENGTH_SHORT).show();
                } else if (phongthi.length() != 3) {
                    // Hiển thị thông báo lỗi khi độ dài của phòng thi không đúng
                    Toast.makeText(getContext(), "Phòng thi chỉ nhập 3 kí tự", Toast.LENGTH_SHORT).show();
                } else {
                    DSDTO objectNew = new DSDTO(ngayThi, ca, phongthi, tenMon);
                    dsDAO.addRow(objectNew);
                    list_ds.clear();
                    list_ds.addAll(dsDAO.getAll());
                    loadDataFromSQL();
                    adapterDs.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void loadDataFromSQL() {
        list_ds = dsDAO.getAll();
        adapterDs = new AdapterDs(list_ds, getActivity());
        rc_ds.setAdapter(adapterDs);

    }


    public void onResume() {
        super.onResume();
        getActivity().setTitle("Quản Lý Danh Sách Thi");
    }
}
