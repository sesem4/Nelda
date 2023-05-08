import os
import xml.etree.ElementTree as ET

def create_properties_on(file_name):
	with open(file_name) as f:
		element_tree = ET.parse(f)

	root = element_tree.getroot()

	properties = root.find("properties")
	if properties is None:
		properties = ET.SubElement(root, "properties")

	enemy_count = properties.find("property[@name='enemyCount']")
	if enemy_count is None:
		properties.append(ET.Element("property", attrib={
		"type": "int",
		"name": "enemyCount",
		"value": "4",
	}))

	enemy_strength = properties.find("property[@name='enemyStrength']")
	if enemy_strength is None:
		properties.append(ET.Element("property", attrib={
			"type": "int",
			"name": "enemyStrength",
			"value": "1",
		}))

	element_tree.write(file_name)


for file in os.listdir("overworld"):
	create_properties_on(f'overworld/{file}')