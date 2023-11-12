package com.upload.dto;

import java.util.Objects;

public class Reader {
	public String Name;
	public String Parent;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getParent() {
		return Parent;
	}
	public void setParent(String parent) {
		Parent = parent;
	}
	@Override
	public int hashCode() {
		return Objects.hash(Name, Parent);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reader other = (Reader) obj;
		return Objects.equals(Name, other.Name) && Objects.equals(Parent, other.Parent);
	}
	@Override
	public String toString() {
		return "Reader [Name=" + Name + ", Parent=" + Parent + "]";
	}
}