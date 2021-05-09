

public class TIMChatAdapter extends  IChatAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == headerViewType) {
            LayoutInflater inflater = LayoutInflater.from(TUIKit.getAppContext());
            return new HeaderViewHolder(inflater.inflate(R.layout.chat_adapter_loadf_more, parent, false));
        }

        LayoutInflater inflater = LayoutInflater.from(TUIKit.getappContext());
        RecyclerViewView.ViewHolder holder = new ChatTextHolder(inflater.inflate(R.layout.chat_apater_text, parent, false));
        switch (viewType) {
            case MessageInfo.MSG_TYPE_TEXT:
                holder = new ChatTextHolder(inflater.inflate(R.layout.chat_adapter_text, parent, false));
                break;

        }
    }


}