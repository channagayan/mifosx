/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.loanaccount.data;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class LoanInstallmentChargeData {

    private final Integer installmentNumber;
    private final LocalDate dueDate;
    @SuppressWarnings("unused")
    private final BigDecimal amount;
    private final BigDecimal amountOutstanding;
    private final boolean paid;
    private final boolean waived;

    public LoanInstallmentChargeData(final Integer installmentNumber, final LocalDate dueDate, final BigDecimal amount,
            final BigDecimal amountOutstanding, final boolean paid, final boolean waived) {
        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.amount = amount;
        this.amountOutstanding = amountOutstanding;
        this.paid = paid;
        this.waived = waived;
    }

    public Integer getInstallmentNumber() {
        return this.installmentNumber;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public boolean isPaymentPending() {
        return !(this.paid || this.waived);
    }

    public BigDecimal getAmountOutstanding() {
        return this.amountOutstanding;
    }

}
