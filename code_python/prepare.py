filename = ["Nokia 6610.txt"]
base_dir = "C:\\Users\\win7\\Desktop\\Sentiment\\data\\customer review data\\"

cc = 0
def get_label(s):
	global cc
	s=s.split(",")
	if len(s) > 1:
		cc+=1

	if "+" in s[0]:
		return "1"
	elif "-" in s[0]:
		return "0"
	else:
		return "none"
def cutword(s):
	return s

data = []

ff = open("outx","w")
for f in filename:
	
	fo = open(base_dir+f)
	prv = "1"

	for line in fo:
		datum = {}

		sp = line.split("##")
		if len(sp) <=1:
			continue

		label = get_label(sp[0])

		if label != "none":
			datum["labe"] = label
			prv = label
		else:
			datum["labe"] = prv

		datum["s"] = cutword(sp[len(sp)-1])
		data.append(datum)
		ff.write(datum["labe"]+"#"+datum["s"])
ff.close()
print cc