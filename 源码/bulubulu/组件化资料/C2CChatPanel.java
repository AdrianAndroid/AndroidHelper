public class C2CChatPanel extends ChatPanel implements IChatPanel{

    private C2CChatPanel mPresenter;
    private C2CChatInfo mBaseInfo;
    private SendMessageListener mSendMessageListener;

    public void setBaseChatId(String chatId){
        mPresenter = new C2ChatPresenter(this);
        mBaseInfo = mPresenter.getC2CChatInfo(chatId);
        if(mBaseInfo == null) {
            return ;
        }
        String chatTitle = mBaeInfo.getChatName();
        mTitleBar.setTitle(chatTitle, PageTitleBar.POSITION.CENTER);
        mPresenter.loadChatMessages(null);
    }


    @Override
    public void exitCht(){
        UIKitAudioArmMachine.getInstance().stopRecord();
        C2CChatManager.getInstance().destroyC2CChat();
    }


    @Override
    public void sendMessage(MessageInfo messageInfo) {
        mPresenter.sendC2CMessage(messageInfo, false);
        if(mSendMessageListener != null) {
            mSendMessageListener.onSendMessage(messageInfo);
        }
    }

    @Override
    public void loadMessage() {
        mPresenter.loadChatMessage(mAdapter.getItemCount() > 0 ? mAdapter.getItem(1) : null);
    }

    @Override
    public void initDefault() {
        super.initDefault();
        mTitleBar.getLeftGroup().setVisibility(View.VISIBLE);
        mTitleBar.getRightGroup().setVisibility(GONE);
        ChatAdapter adapter= new ChatAdapter();
        setChatAdapter(adapter);
        initDefaultEvent();
    }

    @Override
    protected void initPopActions(final MessageInfo msg) {
        if(msg == null) return;
        List<PopMenuAction> actions = new ArrayList<>();
        PoMenuAction action - new PopMenuaction();
        action.setActionName(“删除”);
        action.setActionClickListener(new PopActionClickListener(){
            @Override
            public void onActionclick(int position, Object data) {
                   mPresenter.deleteC2CMessage(position, (MessageInfo)data);
            }
        });
        actions.add(action);
        if(msg.isSelft()){
               action = new popMenuAction();
               action.setActionName(“撤销");
               action.setActionClickListener(new PopActionClickListener(){
                      @Override
                      public void onActionClick(int position, Object data) {
                              mPresenter.revokeC2CMessage(posiiton, (MessageInfo)data);
                      }
               });
               actions.add(action);
               if(msg.getStatus() == MessageInfo.MSG_STATUS_SEND_FAIL){
                       action = new PopMenuAction();
                       action.setActionName("重发“）；
                       action.setActionClickListener(new popActionActionClickListener(){
                               mPresenter.sendC2CMessage(msg, true);
                       }
                       actions.add(actio);
        }
        if(msg.getMsgType() == MessageInfo.MSG_TYPE_TEXT){
               action = new PopMenuAction();
               action.setActionName("复制");
               action.setActionClickListener(new PopActionClickListener() {
                        @Override
                        public void onActionClick(int position, Object data) {
                                copy(msg);
                        }
               }
        }
        sendMessagePopActions(actions, false);
    }
    
    private void copy(MessageInfo msg) {
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager)getContext().getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip;
        myClip  = ClipData.newPlaintText("text", ((TIMTextElem)msg.getTIMMessage().getElement(0).getText());
        mClipboard.setPrimaryClip(myClip);
        Toast.makeText(getContext(), "alreadhy copy to board", Toast.LENTH_SHORT).show();
    }



    @Override
    protected void onDetachedFromWindow(){
            super.onDetachedFromWindow();
            exitChat();
    }
}












