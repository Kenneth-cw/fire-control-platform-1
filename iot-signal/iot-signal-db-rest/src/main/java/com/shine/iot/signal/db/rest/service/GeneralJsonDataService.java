package com.shine.iot.signal.db.rest.service;

import com.platform.model.ServiceRsObjModel;

public interface GeneralJsonDataService {

    ServiceRsObjModel<Boolean> saveGeneralJsonData(String generalJsonData);

}
