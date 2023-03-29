import json

map_width = 16
map_height = 8

alphabet = 'abcdefghijklmnopqrstuvwxyz'.upper()

maps = []
for y in range(map_height):
	for x in range(map_width):
		map = {
            "fileName": f"overworld/{alphabet[x]}{y+1}.tmx",
            "height": 176,
            "width": 256,
            "x": x*256,
            "y": y*176
        }
		maps.append(map)

world = {
	"maps": maps,
    "onlyShowAdjacentMaps": False,
    "type": "world"
}

with open('overworld.world', 'w') as f:
	json.dump(world, f, indent=4)