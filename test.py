arr = [17, 18, 18, 17, 19, 18, 20, 19, 18, 16]
arr2 = [0,0,0,0,1,1,1,0,0,0]
days = 3

leng = len(arr2) - days + 1
result = dict()
for i in range(leng):
    cur = sum(arr2[i:i+days])
    if str(cur) not in result:
        result[str(cur)] = []
    result[str(cur)].append(i)

print(result)