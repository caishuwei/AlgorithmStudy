package com.caisw.algorithm.base;

public class BaseOperation {

    private OutputListener outputListener = new OutputListener() {
        @Override
        public void onOutput(String text) {
            System.out.println(text);
        }
    };

    /**
     * 输出
     *
     * @param o 输出对象
     */
    public void output(Object o) {
        if (o == null) {
            return;
        }
        String s = o.toString();
        if (outputListener != null) {
            outputListener.onOutput(s);
        }
    }

    //getter setter---------------------------------------------------------------------------------
    public OutputListener getOutputListener() {
        return outputListener;
    }

    public void setOutputListener(OutputListener outputListener) {
        this.outputListener = outputListener;
    }
}
