package com.vsrstudio.papaya.model;

import java.util.ArrayList;

public interface GoogleCallback <T> {
    void completedGoogleTask(ArrayList<T> list);
}
