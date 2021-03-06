package com.bloom.web.flower.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.bloom.dao.po.Flower;
import com.bloom.util.mybatis.Page;
import com.bloom.web.flower.FlowerResourceApi;
import com.bloom.web.petal.PetalResourceApi;
import com.bloom.web.petal.PetalVarietyResourceApi;

public class FlowerResourceAssembler extends ResourceAssemblerSupport<Flower, FlowerResource> {

	public FlowerResourceAssembler() {
		super(FlowerResourceApi.class, FlowerResource.class);
	}

	@Override
	public FlowerResource toResource(Flower flower) {
		FlowerResource flowerResource = createResourceWithId(flower.getId(), flower,flower.getGardenerId());
		flowerResource.add(
				linkTo(methodOn(PetalResourceApi.class).flowerPetals(flower.getId(),Page.DEFAULT_PAGE_NO,Page.DEFAULT_PAGE_SIZE)).withRel("petals"),
				linkTo(methodOn(PetalVarietyResourceApi.class).varieties()).withRel("petalVarieties")
				);
		return flowerResource;
	}

	@Override
	protected FlowerResource instantiateResource(Flower flower) {
		return new FlowerResource(flower);
	}

/*	@Override
	public List<FlowerResource> toResources(Iterable<? extends Flower> flowers) {
		LinkedList<FlowerResource> resources = new LinkedList<FlowerResource>();
		flowers.forEach(flower -> {
			FlowerResource resource = toResource(flower);
			if(!resources.isEmpty()) {
				FlowerResource previous = resources.getLast();
				previous.add(linkTo(methodOn(FlowerResourceApi.class).readFlower(flower.getGardenerId(), flower.getId())).withRel(Link.REL_NEXT));
				resource.add(linkTo(methodOn(FlowerResourceApi.class).readFlower(previous.getContent().getGardenerId(), previous.getContent().getId())).withRel(Link.REL_PREVIOUS));
			}
			resources.addLast(resource);
		});
		Link first = linkTo(methodOn(FlowerResourceApi.class).readFlower(resources.getFirst().getContent().getGardenerId(), resources.getFirst().getContent().getId())).withRel(Link.REL_FIRST);
		Link last = linkTo(methodOn(FlowerResourceApi.class).readFlower(resources.getLast().getContent().getGardenerId(), resources.getLast().getContent().getId())).withRel(Link.REL_LAST);
		resources.stream().forEach(r -> {
			r.add(first,last);
		});
		return resources;
	}*/

	
}
