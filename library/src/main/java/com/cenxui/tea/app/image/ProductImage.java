package com.cenxui.tea.app.image;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProductImage {
    String filePath;
    String productName;
    String version;
    String fileName;
}
