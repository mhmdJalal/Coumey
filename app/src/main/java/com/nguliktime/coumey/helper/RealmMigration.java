package com.nguliktime.coumey.helper;

import com.nguliktime.coumey.model.KegiatanModel;
import com.nguliktime.coumey.model.PengeluaranModel;

import io.realm.DynamicRealm;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        if (oldVersion == 0){
            schema.create("KegiatanModel")
                    .addField("id", KegiatanModel.class)
                    .addField("kegiatan", KegiatanModel.class)
                    .addField("divisi", KegiatanModel.class)
                    .addField("keterangan", KegiatanModel.class)
                    .addField("tanggal", KegiatanModel.class);
            oldVersion++;
        }

        if (oldVersion == 1){
            final RealmObjectSchema spendingschema = schema.get("PengeluaranModel");
            spendingschema.addField("bulan", PengeluaranModel.class);
            spendingschema.addField("idBulan", PengeluaranModel.class);
            oldVersion++;
        }
    }
}
