package com.aleksandrov.phonechecker.services.Update;

import java.util.List;

public interface UpdateController {
    void performUpdate(List<String> updateStatusList, Runnable endUpdateState);
}
