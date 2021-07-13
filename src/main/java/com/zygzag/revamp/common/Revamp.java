package com.zygzag.revamp.common;

import com.zygzag.revamp.common.entity.DrifterEggEntity;
import com.zygzag.revamp.client.render.DrifterEggRenderer;
import com.zygzag.revamp.common.util.EmpowermentData;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod("revamp")
public class Revamp {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "revamp";
    public static List<EmpowermentData> EMPOWERMENT_DATA_LIST = new ArrayList<>();

    public Revamp() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerAttributes);

        Registry.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // this.readJsonFiles();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // RenderingRegistry.registerEntityRenderingHandler(Registry.CUSTOM_IRON_GOLEM.get(), CustomIronGolemRenderer::new);
        // RenderingRegistry.registerEntityRenderingHandler(Registry.ABOMINATION_WITHER.get(), AbominationWitherRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Registry.DRIFTER_EGG.get(), DrifterEggRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) { }

    private void processIMC(final InterModProcessEvent event) { }

    private void registerAttributes(final EntityAttributeCreationEvent event) {
        // event.put(Registry.CUSTOM_IRON_GOLEM.get(), CustomIronGolemEntity.createAttributes().build());
        // event.put(Registry.ABOMINATION_WITHER.get(), AbominationWitherEntity.createAttributes().build());
        event.put(Registry.DRIFTER_EGG.get(), DrifterEggEntity.createAttributes().build());
    }

    public static final ItemGroup TAB = new ItemGroup("main_tab") {
        @Override
        public ItemStack makeIcon() {
            return Registry.IRIDIUM_PLATING.get().getDefaultInstance();
        }
    };

    private void readJsonFiles() {
        /*try {
            ArrayList<EmpowermentData> list = new ArrayList<>();
            Gson gson = new Gson();
            ResourceLocation loc = new ResourceLocation("revamp", "misc/empowerables.json");
            InputStream in = Minecraft.getInstance().getResourceManager().getResource(loc).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JsonElement je = gson.fromJson(reader, JsonElement.class);
            JsonObject j = je.getAsJsonObject();
            EMPOWERMENT_DATA_LIST = list;
            LOGGER.info("empowerables");
            LOGGER.info(j);
            Stream<String> set = j.entrySet().stream().map(Map.Entry::getKey);
            for (String s : set.collect(Collectors.toList())) {
                String[] s1 = s.split(":");
                JsonObject json = j.getAsJsonObject(s);
                EmpowermentData data = new EmpowermentData(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(s1[0], s1[1])), json.get("multiplier").getAsInt(), json.get("threshold").getAsInt());
                if (json.get("abominate").getAsBoolean()) {
                    String[] s2 = json.get("abomination").getAsString().split(":");
                    data = EmpowermentData.AbominationEmpowermentData.fromRegularData(data, ForgeRegistries.ENTITIES.getValue(new ResourceLocation(s2[0], s2[1])));
                }
                list.add(data);
            }
            EMPOWERMENT_DATA_LIST = list;
            LOGGER.info(list);
        } catch (IOException ex) {
            ex.printStackTrace();
            LOGGER.warn("Empowerables json not found. Assuming intentional, treating as empty file");
        }*/
    }
}