package com.cleardragonf.ourmod.entity.client;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.animations.ModAnimationDefinitions;
import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class JackOSurpriseModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart jackosurprise;
	private final ModelPart head;
	private final ModelPart arms;
	private final ModelPart right_arm;
	private final ModelPart upper_arm;
	private final ModelPart b_lower_arm;
	private final ModelPart left_arm;
	private final ModelPart hips;
	private final ModelPart torso;
	private final ModelPart legs;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public JackOSurpriseModel(ModelPart root) {
		this.jackosurprise = root.getChild("jackosurprise");
		this.head = root.getChild("jackosurprise").getChild("head");
		this.arms = root.getChild("jackosurprise").getChild("arms");
		this.right_arm = root.getChild("jackosurprise").getChild("arms").getChild("right_arm");
		this.upper_arm = root.getChild("jackosurprise").getChild("arms").getChild("right_arm").getChild("upper_arm");
		this.b_lower_arm = root.getChild("jackosurprise").getChild("arms").getChild("right_arm").getChild("b_lower_arm");
		this.left_arm = root.getChild("jackosurprise").getChild("arms").getChild("left_arm");
		this.hips = root.getChild("jackosurprise").getChild("hips");
		this.torso = root.getChild("jackosurprise").getChild("torso");
		this.legs = root.getChild("jackosurprise").getChild("legs");
		this.left_leg = root.getChild("jackosurprise").getChild("legs").getChild("left_leg");
		this.right_leg = root.getChild("jackosurprise").getChild("legs").getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition jackosurprise = partdefinition.addOrReplaceChild("jackosurprise", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = jackosurprise.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(7.0F, -12.0F, 1.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition arms = jackosurprise.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(5.0F, -21.0F, 0.0F));

		PartDefinition upper_arm = right_arm.addOrReplaceChild("upper_arm", CubeListBuilder.create().texOffs(3, 0).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition b_lower_arm = right_arm.addOrReplaceChild("b_lower_arm", CubeListBuilder.create().texOffs(6, 3).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(3, 6).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(36, 30).addBox(-1.0F, 3.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -21.0F, 0.0F));

		PartDefinition hips = jackosurprise.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition torso = jackosurprise.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -11.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition legs = jackosurprise.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(4, 18).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 18).addBox(-2.0F, -12.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 6.0F, 0.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -18.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 30).addBox(-3.0F, -12.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		// Animating walk
		this.animateWalk(ModAnimationDefinitions.MODEL_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);

		// Animating idle
		this.animate(((JackOSurpriseEntity) entity).idleAnimationState, ModAnimationDefinitions.MODEL_IDLE, ageInTicks, 1f);

		this.animate(((JackOSurpriseEntity) entity).spawnAnimationState, ModAnimationDefinitions.MODEL_SPAWN, ageInTicks, 1f);

		this.animate(((JackOSurpriseEntity) entity).attackAnimationState, ModAnimationDefinitions.MODEL_ATTACK, ageInTicks, 1f);


	}


	private void applyHeadRotation(float NetHeadYaw, float HeadPitch, float agerinTicks){
		 NetHeadYaw = Mth.clamp(NetHeadYaw, -30.0F, 30.0F);
		 HeadPitch = Mth.clamp(HeadPitch, -25.0F, 45.0F);

		 this.head.yRot = NetHeadYaw * ((float)Math.PI / 180F);
		 this.head.xRot = HeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		jackosurprise.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return jackosurprise;
	}
}