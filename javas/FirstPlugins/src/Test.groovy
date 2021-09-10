import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.annotations.NotNull

class Test extends AnAction{
    @Override
    void actionPerformed(@NotNull AnActionEvent anActionEvent) {

    }

    @Override
    boolean isDumbAware() {
        return super.isDumbAware()
    }
}
