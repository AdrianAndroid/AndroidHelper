package closure

class View {
    private Closure onClickListener
    Timer timer

    View() {
        timer = new Timer()
        timer.schedule(new TimerTask() {
            @Override
            void run() {
                preOnClick()
            }
        }, 0, 300)
    }

    void setOnClickListener(Closure closure) {
        this.onClickListener = closure
    }

    private void preOnClick() {
        if (onClickListener != null) {
            onClickListener(this)
        }
    }


    @Override
    public String toString() {
        return "this is view";
    }
}
