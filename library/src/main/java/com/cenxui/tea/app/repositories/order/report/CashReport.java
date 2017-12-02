package com.cenxui.tea.app.repositories.order.report;

import com.cenxui.tea.app.repositories.order.PaidOrderKey;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class CashReport {
    List<Receipt> receipts;
    Double revenue;
    PaidOrderKey lastKey;
}
