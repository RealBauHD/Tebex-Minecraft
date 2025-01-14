package io.tebex.plugin.command.sub;

import com.mojang.brigadier.context.CommandContext;
import dev.dejvokep.boostedyaml.YamlDocument;
import io.tebex.plugin.TebexPlugin;
import io.tebex.plugin.command.SubCommand;
import io.tebex.sdk.SDK;
import io.tebex.sdk.exception.ServerNotFoundException;
import io.tebex.sdk.platform.config.ServerPlatformConfig;
import io.tebex.sdk.util.StringUtil;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.IOException;

public class DebugCommand extends SubCommand {
    public DebugCommand(TebexPlugin platform) {
        super(platform, "debug", "tebex.debug");
    }

    @Override
    public void execute(CommandContext<ServerCommandSource> context) {
        final ServerCommandSource source = context.getSource();
        TebexPlugin platform = getPlatform();

        ServerPlatformConfig config = platform.getPlatformConfig();
        YamlDocument configFile = config.getYamlDocument();

        String input = context.getArgument("trueOrFalse", String.class);
        if (StringUtil.isTruthy(input)) {
            context.getSource().sendMessage(Text.of("§b[Tebex] §7Debug mode enabled."));
            config.setVerbose(true);
            configFile.set("verbose", true);
        } else if (StringUtil.isFalsy(input)) {
            context.getSource().sendMessage(Text.of("§b[Tebex] §7Debug mode disabled."));
            config.setVerbose(false);
            configFile.set("verbose", false);
        } else {
            context.getSource().sendMessage(Text.of("§b[Tebex] §7Invalid command usage. Use /tebex " + this.getName() + " " + getUsage()));
        }

        try {
            configFile.save();
        } catch (IOException e) {
            context.getSource().sendMessage(Text.of("§b[Tebex] §7Failed to save configuration file."));
        }
    }

    @Override
    public String getDescription() {
        return "Enables more verbose logging.";
    }

    @Override
    public String getUsage() {
        return "<trueOrFalse>";
    }
}
