package me.axiles.guiRender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("fps_mod")
public class Fpsrender {

    private static final Logger LOGGER = LogManager.getLogger();

    // Переменные для подсчета FPS
    private long lastTime = System.currentTimeMillis();
    private int frames = 0;
    private int currentFps = 0;

    public Fpsrender() {
        // Регистрируем ЭТОТ класс как слушатель событий
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Клиентский мод успешно загружен!");
    }

    /**
     * Событие отрисовки интерфейса. Срабатывает каждый кадр.
     * Именно здесь мы рисуем текст поверх игры.
     */
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        // Рисуем только когда тип оверлея "ALL" (поверх всего)
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {

            // 1. Считаем FPS
            frames++;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= 1000) { // Если прошла 1 секунда
                currentFps = frames;
                frames = 0;
                lastTime = currentTime;
            }

            // 2. Получаем доступ к шрифту и матрице стека
            Minecraft mc = Minecraft.getInstance();
            FontRenderer font = mc.font;

            // 3. Формируем текст
            String text = "FPS: " + currentFps + " | Client Mod Active";

            // 4. Рисуем текст (x=5, y=5, цвет=белый 0xFFFFFFFF)
            // В 1.16.5 используется MatrixStack из события
            font.draw(event.getMatrixStack(), text, 5, 5, 0xFFFFFFFF);
        }
    }
}