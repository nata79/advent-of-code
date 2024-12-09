# disk_map = '2333133121414131402'
disk_map = File.read('day9.txt').strip

disk = []

files = []
freespace = []

pointer = 0
disk_map = disk_map.split('').map(&:to_i).each_with_index.map do |x, i|
  if i.even?
    x.times { disk << (i/2) }
    files << { id: i/2, size: x, location: pointer }
  else
    x.times { disk << nil }
    freespace << [x, pointer]
  end
  pointer += x
end

def checksum(disk)
  total = 0

  disk.each_with_index do |x, i|
    total += x * i if x  
  end

  total
end

(disk.size - 1).downto(0) do |i|
  if disk[i]
    0.upto(i - 1) do |j|
      if disk[j].nil?
        disk[j] = disk[i]
        disk[i] = nil
        break
      end
    end
  end
end

p checksum(disk)

def get_disk(files, size)
  disk = Array.new(size)

  files.each do |file|
    file[:location].upto(file[:location] + file[:size]-1) do |i|
      disk[i] = file[:id]
    end
  end

  disk
end

files.reverse.each_with_index do |file, i|
  freespace.sort_by { _1[1] }.each do |space|
    if space[0] >= file[:size] && space[1] < file[:location]
      freespace << [file[:size], file[:location]]
      file[:location] = space[1]
      
      if space[0] == file[:size]
        freespace.delete(space)
      else
        space[0] -= file[:size]
        space[1] += file[:size]
      end

      break
    else      
    end
  end
end

p checksum(get_disk(files, disk_map.size))